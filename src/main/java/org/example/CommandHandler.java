package org.example;

import java.io.*;
import java.util.Scanner;

public class CommandHandler {
    private final Library library;
    private final Scanner scanner;
    private final PrintStream fileOut;

    public CommandHandler(Library library, Scanner scanner, PrintStream fileOut) {
        this.library = library;
        this.scanner = scanner;
        this.fileOut = fileOut;
    }

    public void processCommand(String cmd) {
        switch (cmd) {
            case "add" -> {
                var title = scanner.next();
                var author = scanner.next();
                var releaseDate = scanner.nextInt();
                var status = BookStatus.valueOf(scanner.next().toUpperCase());
                fileOut.println(cmd + title + " " + author + " " + releaseDate + " " + status);
                library.addBook(new Book(title, author, releaseDate, status));
            }
            case "remove" -> {
                var index = scanner.nextInt();
                fileOut.println("remove " + index);
                library.removeBook(index);
            }
            case "edit" -> {
                var title = scanner.next();
                var author = scanner.next();
                var releaseDate = scanner.nextInt();
                var status = BookStatus.valueOf(scanner.next().toUpperCase());
                fileOut.println("edit " + title + " " + author + " " + releaseDate + " " + status);
                library.getBook(scanner.nextInt())
                        .setTitle(scanner.next()).setAuthor(scanner.next())
                        .setReleaseDate(scanner.nextInt())
                        .setStatus(BookStatus.valueOf(scanner.next().toUpperCase()));
            }
            case "search" -> {
                String type = scanner.next();
                String query = scanner.next();
                fileOut.println("search " + type + " " + query);
                handleSearch(type, query);
            }
            case "read" -> {
                var fileName = scanner.next();
                fileOut.println("read " + fileName);
                readFile(fileName);
            }
            case "write" -> {
                var fileName = scanner.next();
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

    private void handleSearch(String type,String query) {
        fileOut.println("search " + type + " " + query);
        var list = library.getBooks();
        var indexes = type.equals("author") ? library.searchBooksByAuthorIndexes(query)
                : library.searchBooksByTitleIndexes(query);
        for (int i : indexes) printAndLog(i + ": " + list.get(i));
    }

    private void readFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            printAndLog("File not found: " + filename);
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            library.getBooks().clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",", 0);
                if (p.length == 4)
                    library.addBook(new Book(p[0].trim(), p[1].trim(),
                            Integer.parseInt(p[2].trim()), BookStatus.valueOf(p[3].trim())));
            }
            printAndLog("Books loaded from " + filename);
        } catch (IOException e) {
            printAndLog("Error reading file: " + e.getMessage());
        }
    }

    private void writeFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Book b : library.getBooks()) {
                bw.write(b.getTitle() + "," + b.getAuthor() + "," + b.getReleaseDate() + "," + b.getStatus());
                bw.newLine();
            }
            printAndLog("Books saved to " + filename);
        } catch (IOException e) {
            printAndLog("Error writing file: " + e.getMessage());
        }
    }

    private void printHelp() {
        printAndLog("add [Title] [Author] [Year] [Status]");
        printAndLog("remove [Index]");
        printAndLog("edit [Index] [Title] [Author] [Year] [Status]");
        printAndLog("search author|title [text]");
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
}