package org.example.controller;

import org.example.model.*;
import org.example.service.Library;
import org.example.util.FieldHandler;

import java.io.*;
import java.util.*;
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
                fileOut.print("add " + cmd + " " + asset);
                System.out.println(asset + " added");
            }

            case "remove" -> {
                var index = nextIntArg();
                var asset = library.getAsset(index);
                library.removeAsset(asset);
                System.out.println(asset + " removed");
                fileOut.println("remove " + index);
            }

            case "edit" -> {
                var oldAsset = library.getAsset(nextIntArg());
                var newAsset = readAsset(oldAsset.getClass().getSimpleName());
                fileOut.println("edit " + oldAsset + "->" + newAsset);
                System.out.println(oldAsset + " edited to " + newAsset);
                FieldHandler.copyFields(newAsset, oldAsset);
            }

            case "search" -> {
                List<String> assetTypes = List.of(nextStrArg().split(" "));
                List<String> fields = List.of(nextStrArg().split(" "));
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
        var filtered = FieldHandler.searchFields(library.getAssets(), fields, searchTerm);
        filtered = filtered.stream().filter((asset) -> assetTypes.contains(asset.getClass().getSimpleName())).toList();
        if (filtered.isEmpty()) System.out.println("No assets found !!!");
        for (var asset : filtered) System.out.println(asset);
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
        printAndLog("search [Magazine|Book|Reference|Thesis] [Title|Author|Year|Publisher|Status] [text]");
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
        return Objects.requireNonNull(this.args.poll()).trim();
    }

    private int nextIntArg() {
        return Integer.parseInt(nextStrArg());
    }
}