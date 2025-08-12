package org.example.controller;

import org.example.model.*;
import org.example.service.Library;
import org.example.util.IO.InputParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {
//    private Library library;
//    private ByteArrayOutputStream outContent;
//    private CommandHandler handler;
//
//    @BeforeEach
//    void setUp() {
//        library = new Library();
//        outContent = new ByteArrayOutputStream();
//        handler = new CommandHandler(library, new PrintStream(outContent));
//    }
//
//    @Test
//    void processCommand_add_addsAssetsToLibrary() {
//        handler.processCommand("add", InputParser.parseQuotedInput("book \"Animal village\" Alex 1988 exist\n"));
//        handler.processCommand("add", InputParser.parseQuotedInput("thesis \"the science\" reza 2023"));
//        handler.processCommand("add", InputParser.parseQuotedInput("reference \"first transformer\" hasan 2024"));
//        handler.processCommand("add", InputParser.parseQuotedInput("magazine \"car magazine\" Max 2025 \"Science Journal\""));
//
//        assertEquals(4, library.getAssets().size());
//        Asset asset0 = library.getAsset(0);
//        Asset asset1 = library.getAsset(1);
//        Asset asset2 = library.getAsset(2);
//        Asset asset3 = library.getAsset(3);
//
//        assertTrue(outContent.toString().contains(asset0.toString()));
//        assertTrue(outContent.toString().contains(asset1.toString()));
//        assertTrue(outContent.toString().contains(asset2.toString()));
//        assertTrue(outContent.toString().contains(asset3.toString()));
//
//        assertSame(Book.class, asset0.getClass());
//        assertSame(Thesis.class, asset1.getClass());
//        assertSame(Reference.class, asset2.getClass());
//        assertSame(Magazine.class, asset3.getClass());
//
//        assertEquals("Animal village", library.getAsset(0).getTitle());
//        assertEquals("the science", library.getAsset(1).getTitle());
//        assertEquals("first transformer", library.getAsset(2).getTitle());
//        assertEquals("car magazine", library.getAsset(3).getTitle());
//    }
//
//    @Test
//    void processCommand_addInputWithoutSpace_addsAssetToLibrary() {
//        handler.processCommand("add", InputParser.parseQuotedInput("book Rings Tolkien 2000 EXIST"));
//
//        assertEquals(1, library.getAssets().size());
//        Asset asset = library.getAsset(0);
//        assertEquals("Rings", asset.getTitle());
//        assertEquals("Tolkien", asset.getAuthor());
//        assertEquals(2000, asset.getReleaseDate());
//        assertEquals(BookStatus.EXIST, ((Book) asset).getStatus());
//        assertTrue(outContent.toString().contains(asset.toString()));
//    }
//
//    @Test
//    void processCommand_remove_removesAssetByIndex() {
//        library.addAsset(new Book("T", "A", 2000, BookStatus.EXIST));
//        handler.processCommand("remove", InputParser.parseQuotedInput("0"));
//
//        assertTrue(library.getAssets().isEmpty());
//        assertTrue(outContent.toString().contains("remove 0"));
//    }
//
//    @Test
//    void processCommand_searchMultipleFields_findsBook() {
//        handler.processCommand("add", InputParser.parseQuotedInput("book \"Animal village\" Alex 1988 exist\n"));
//        handler.processCommand("add", InputParser.parseQuotedInput("thesis \"the science\" reza 2023"));
//        handler.processCommand("add", InputParser.parseQuotedInput("reference \"first transformer\" hasan 2024"));
//        handler.processCommand("add", InputParser.parseQuotedInput("magazine \"car magazine\" Max 2025 \"Science Journal\""));
//
//        Asset asset0 = library.getAsset(0);
//        Asset asset1 = library.getAsset(1);
//        Asset asset2 = library.getAsset(2);
//        Asset asset3 = library.getAsset(3);
//
//        handler.processCommand("search", InputParser.parseQuotedInput("Asset \"title author\" a"));
//
//        String output = outContent.toString();
//        System.out.println(output);
//        assertTrue(output.contains(asset0.toString()));
//        assertTrue(output.contains(asset1.toString()));
//        assertTrue(output.contains(asset2.toString()));
//        assertTrue(output.contains(asset3.toString()));
//    }
//
//    @Test
//    void processCommand_searchBySingleField_findsBook() {
//        library.addAsset(new Book("MyTitle", "Auth", 2000, BookStatus.EXIST));
//        handler.processCommand("search", InputParser.parseQuotedInput("Asset title My"));
//
//        String output = outContent.toString();
//        assertTrue(output.contains("search [title] in [Asset]"));
//    }
//
//    @Test
//    void processCommand_read_nonExistingFile_printsError() {
//        CommandHandler handler = createHandlerWithInput();
//        handler.processCommand("read", InputParser.parseQuotedInput("nofile.txt"));
//
//        assertTrue(outContent.toString().contains("File not found: nofile.txt"));
//    }
//
//    @Test
//    void processCommand_writeAndRead_writesAndReadsBooks() throws IOException {
//        File tempFile = File.createTempFile("books", ".txt");
//        tempFile.deleteOnExit();
//
//        // Prepare library with a asset
//        library.addAsset(new Asset("T", "A", 2000, BookStatus.EXIST));
//
//        // Write to file
//        CommandHandler writeHandler = createHandlerWithInput();
//        writeHandler.processCommand("write", InputParser.parseQuotedInput(tempFile.getAbsolutePath()));
//        assertTrue(outContent.toString().contains("Books saved to"));
//
//        CommandHandler readHandler = createHandlerWithInput();
//        readHandler.processCommand("read", InputParser.parseQuotedInput(tempFile.getAbsolutePath()));
//
//        assertEquals(1, library.getAssets().size());
//        assertEquals("T", library.getAsset(0).getTitle());
//    }
//
//    @Test
//    void writeFile_shouldCreateDirectoriesAndSaveFile() throws IOException {
//        library.addAsset(new Asset("TestTitle", "TestAuthor", 2024, BookStatus.EXIST));
//
//        // Create path inside a temp folder that doesn't exist yet
//        File tempDir = Files.createTempDirectory("librarytest").toFile();
//        File subDir = new File(tempDir, "nested/folder");
//        File targetFile = new File(subDir, "books.txt");
//
//        CommandHandler writeHandler = createHandlerWithInput();
//        writeHandler.processCommand("write", InputParser.parseQuotedInput(targetFile.getAbsolutePath()));
//        assertTrue(outContent.toString().contains("Books saved to"));
//
//
//        // Assert
//        assertTrue(targetFile.exists(), "File should be created");
//        String output = Files.readString(targetFile.toPath());
//        System.out.println(targetFile.toPath());
//        assertTrue(output.contains("TestTitle"), "File should contain asset data");
//
//        // Cleanup
//        tempDir.deleteOnExit();
//    }
//
//    @Test
//    void processCommand_printall_printsBooks() {
//        library.addAsset(new Asset("T", "A", 2000, BookStatus.EXIST));
//        CommandHandler handler = createHandlerWithInput();
//        handler.processCommand("printall", InputParser.parseQuotedInput(""));
//
//        assertTrue(outContent.toString().contains("printall"));
//        assertTrue(outContent.toString().contains("0: Asset"));
//    }
//
//    @Test
//    void processCommand_help_printsHelpText() {
//        CommandHandler handler = createHandlerWithInput();
//        handler.processCommand("--help", InputParser.parseQuotedInput(""));
//
//        String output = outContent.toString();
//        assertTrue(output.contains("add [Title] [Author] [Year] [Status]"));
//        assertTrue(output.contains("remove [Index]"));
//        assertTrue(output.contains("exit"));
//    }
//
//    @Test
//    void processCommand_unknownCommand_printsError() {
//        CommandHandler handler = createHandlerWithInput();
//        handler.processCommand("unknown", InputParser.parseQuotedInput(""));
//
//        String output = outContent.toString();
//        assertTrue(output.contains("Invalid command"));
//        assertTrue(output.contains("Unknown command"));
//    }
}
