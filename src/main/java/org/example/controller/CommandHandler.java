package org.example.controller;

import org.example.model.*;
import org.example.service.Library;
import org.example.util.FieldHandler;
import org.example.util.LinkedList2;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandHandler {
    private final Library library;
    private final PrintStream fileOut;
    private Queue<String> args;

    public CommandHandler(Library library, PrintStream fileOut) {
        this.library = library;
        this.fileOut = fileOut;
    }

    public void processCommand(String cmd, Queue<String> args) {
        this.args = args;
        switch (cmd) {
            case "add" -> {
                cmd = nextStrArg();
                Asset asset = readAsset(cmd);
                library.addAsset(asset);
                printAndLog(asset + " added");
            }

            case "remove" -> {
                var index = nextIntArg();
                var asset = library.getAsset(index);
                if (asset == null) {
                    System.out.println("not found!!\nfirst search then remove");
                    return;
                }
                library.removeAsset(asset);
                printAndLog(asset + " removed");
            }

            case "edit" -> {
                var oldAsset = library.getAsset(nextIntArg());
                if (oldAsset == null) {
                    System.out.println("not found!!\nfirst search then remove");
                    return;
                }
                var newAsset = readAsset(oldAsset.getClass().getSimpleName());
                printAndLog(oldAsset + " edited to " + newAsset);
                FieldHandler.copyFields(newAsset, oldAsset);
            }

            case "search" -> {
                List<String> assetTypes = Arrays.stream(nextStrArg().split(" "))
                        .map(String::trim)
                        .map((word) -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                        .collect(Collectors.toList());
                List<String> fields = Arrays.stream(nextStrArg().split(" "))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .map((word) -> word.equals("releasedate") ? "releaseDate" : word)
                        .collect(Collectors.toList());
                var searchTerm = nextStrArg();
                fileOut.println("search " + fields + " in " + assetTypes);
                handleSearch(assetTypes, fields, searchTerm);
            }

            case "read" -> {
                var fileName = nextStrArg();
                fileOut.println("read " + fileName);
                readFile(fileName);
            }

            case "write" -> {
                var fileName = nextStrArg();
                fileOut.println("write " + fileName);
                writeFile(fileName);
            }

            case "sort" -> {
                List<Asset> list=
                switch (nextStrArg()) {
                    case "all" -> library.getAssets();
                    case "search" -> library.getSearchedAssets();
                    default -> null;
                };
                if (list == null) {
                    printAndLog("Invalid argument");
                    return;
                }
                var comparator = switch (nextStrArg()){
                    case "title" -> Comparator.comparing(Asset::getTitle);
                    case "releasedate" -> Comparator.comparing(Asset::getReleaseDate);
                    case "author" -> Comparator.comparing(Asset::getAuthor);
                    default -> null;
                };
                if (comparator == null) {
                    printAndLog("Invalid argument");
                }
                if (list.isEmpty())
                {
                    printAndLog("No assets found");
                    return;
                }
                list.sort(comparator);
                list.forEach(System.out::println);
            }

            case "printall" -> {
                fileOut.println("printall");
                printAndLog(library);
            }

            case "--help" -> {
                fileOut.println("--help");
                printHelp();
            }

            case "exit" -> {
                fileOut.println("exit");
                System.exit(0);
            }

            default -> {
                fileOut.println("Invalid command");
                printAndLog("Unknown command: --help for help");
            }
        }
    }

    private void handleSearch(List<String> assetTypes, List<String> fields, String searchTerm) {
        library.searchAssets(assetTypes, fields, searchTerm);
        library.printSearchedAssets();
    }

    private Asset readAsset(String cmd) {
        return switch (cmd.toLowerCase()) {
            case "magazine" -> new Magazine(nextStrArg(), nextStrArg(), nextIntArg(), nextStrArg());
            case "book" ->
                    new Book(nextStrArg(), nextStrArg(), nextIntArg(), BookStatus.valueOf(nextStrArg().toUpperCase()));
            case "reference" -> new Reference(nextStrArg(), nextStrArg(), nextIntArg());
            case "thesis" -> new Thesis(nextStrArg(), nextStrArg(), nextIntArg());
            default -> null;
        };
    }

    private void readFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            printAndLog("File not found: " + filename);
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            library.getAssets().clear();
            String line;
            while ((line = br.readLine()) != null) {
                args.addAll(List.of(line.split(",", 0)));
                library.addAsset(readAsset(nextStrArg().toLowerCase()));
            }
            printAndLog("Books loaded from " + filename);
        } catch (IOException e) {
            printAndLog("Error reading file: " + e.getMessage());
        }
    }

    private void writeFile(String filename) {
        File file = new File(filename);
        file.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (CSVAble asset : library.getAssets()) {
                bw.write(asset.toCSV());
                bw.newLine();
            }
            printAndLog("Books saved to " + filename);
        } catch (IOException e) {
            printAndLog("Error writing file: " + e.getMessage());
        }
    }

    private void printHelp() {
        printAndLog("add Magazine [Title] [Author] [Year] [Publisher]");
        printAndLog("add Book [Title] [Author] [Year] [Status]");
        printAndLog("add Reference [Title] [Author] [Year]");
        printAndLog("add Thesis [Title] [Author] [Year]");
        printAndLog("remove [Index]");
        printAndLog("edit [Index] [Title] [Author] [Year] [Status]");
        printAndLog("search [Magazine|Book|Reference|Thesis] [title|author|year|publisher|status] [text]");
        printAndLog("sort [All|Search] [title|author|year]");
        printAndLog("read [File]");
        printAndLog("write [File]");
        printAndLog("printall");
        printAndLog("exit");
    }

    private void printAndLog(Object object) {
        printAndLog(object.toString());
    }

    private void printAndLog(String message) {
        System.out.println(message);
        this.fileOut.println(message);
    }

    private String nextStrArg() {
        try {
            return Objects.requireNonNull(this.args.poll()).trim();
        } catch (NullPointerException e) {
            printAndLog("No arguments provided !!!");
            return "";
        }
    }

    private int nextIntArg() {
        var arg = nextStrArg();
        try {
            return Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            printAndLog(arg + " is invalid argument !!!");
            printAndLog("this should be an integer");
            return -1;
        }
    }
}