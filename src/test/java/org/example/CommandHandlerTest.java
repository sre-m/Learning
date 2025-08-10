package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {

    private Library library;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        library = new Library();
        outContent = new ByteArrayOutputStream();
    }

    private CommandHandler createHandlerWithInput() {
        return new CommandHandler(library, new PrintStream(outContent));
    }

    @Test
    void processCommand_add_addsBookToLibrary() {
        CommandHandler handler = createHandlerWithInput();
        handler.processCommand("add", InputParser.parseQuotedInput("Title Author 2000 EXIST"));

        assertEquals(1, library.getBooks().size());
        Book book = library.getBook(0);
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals(2000, book.getReleaseDate());
        assertEquals(BookStatus.EXIST, book.getStatus());
        assertTrue(outContent.toString().contains("add Title Author 2000 EXIST"));
    }

    @Test
    void processCommand_addInputWithSpace_addsBookToLibrary() {
        CommandHandler handler = createHandlerWithInput();
        handler.processCommand("add", InputParser.parseQuotedInput("\"The Lord of the Rings\" \"J.R.R. Tolkien\" 2000 EXIST"));

        assertEquals(1, library.getBooks().size());
        Book book = library.getBook(0);
        assertEquals("The Lord of the Rings", book.getTitle());
        assertEquals("J.R.R. Tolkien", book.getAuthor());
        assertEquals(2000, book.getReleaseDate());
        assertEquals(BookStatus.EXIST, book.getStatus());
        System.out.println(outContent.toString());
        assertTrue(outContent.toString().contains("add The Lord of the Rings J.R.R. Tolkien 2000 EXIST"));
    }

    @Test
    void processCommand_remove_removesBookByIndex() {
        library.addBook(new Book("T", "A", 2000, BookStatus.EXIST));
        CommandHandler handler = createHandlerWithInput();
        handler.processCommand("remove",InputParser.parseQuotedInput("0"));

        assertTrue(library.getBooks().isEmpty());
        assertTrue(outContent.toString().contains("remove 0"));
    }

    @Test
    void processCommand_searchByTitle_findsBook() {
        library.addBook(new Book("MyTitle", "Auth", 2000, BookStatus.EXIST));
        CommandHandler handler = createHandlerWithInput();
        handler.processCommand("search",InputParser.parseQuotedInput("title My"));

        String output = outContent.toString();
        assertTrue(output.contains("search title My"));
        assertTrue(output.contains("0: Book"));
    }

    @Test
    void processCommand_searchByAuthor_findsBook() {
        library.addBook(new Book("Title", "SomeAuthor", 2000, BookStatus.EXIST));
        CommandHandler handler = createHandlerWithInput();
        handler.processCommand("search", InputParser.parseQuotedInput("author Some"));

        String output = outContent.toString();
        assertTrue(output.contains("search author Some"));
        assertTrue(output.contains("0: Book"));
    }

    @Test
    void processCommand_read_nonExistingFile_printsError() {
        CommandHandler handler = createHandlerWithInput();
        handler.processCommand("read",InputParser.parseQuotedInput("nofile.txt"));

        assertTrue(outContent.toString().contains("File not found: nofile.txt"));
    }

    @Test
    void processCommand_writeAndRead_writesAndReadsBooks() throws IOException {
        File tempFile = File.createTempFile("books", ".txt");
        tempFile.deleteOnExit();

        // Prepare library with a book
        library.addBook(new Book("T", "A", 2000, BookStatus.EXIST));

        // Write to file
        CommandHandler writeHandler = createHandlerWithInput();
        writeHandler.processCommand("write", InputParser.parseQuotedInput(tempFile.getAbsolutePath()));
        assertTrue(outContent.toString().contains("Books saved to"));

        CommandHandler readHandler = createHandlerWithInput();
        readHandler.processCommand("read", InputParser.parseQuotedInput(tempFile.getAbsolutePath()));

        assertEquals(1, library.getBooks().size());
        assertEquals("T", library.getBook(0).getTitle());
    }

    @Test
    void writeFile_shouldCreateDirectoriesAndSaveFile() throws IOException {
        library.addBook(new Book("TestTitle", "TestAuthor", 2024, BookStatus.EXIST));

        // Create path inside a temp folder that doesn't exist yet
        File tempDir = Files.createTempDirectory("librarytest").toFile();
        File subDir = new File(tempDir, "nested/folder");
        File targetFile = new File(subDir, "books.txt");

        CommandHandler writeHandler = createHandlerWithInput();
        writeHandler.processCommand("write", InputParser.parseQuotedInput(targetFile.getAbsolutePath()));
        assertTrue(outContent.toString().contains("Books saved to"));



        // Assert
        assertTrue(targetFile.exists(), "File should be created");
        String output = Files.readString(targetFile.toPath());
        System.out.println(targetFile.toPath());
        assertTrue(output.contains("TestTitle"), "File should contain book data");

        // Cleanup
        tempDir.deleteOnExit();
    }
    @Test
    void processCommand_printall_printsBooks() {
        library.addBook(new Book("T", "A", 2000, BookStatus.EXIST));
        CommandHandler handler = createHandlerWithInput();
        handler.processCommand("printall",InputParser.parseQuotedInput(""));

        assertTrue(outContent.toString().contains("printall"));
        assertTrue(outContent.toString().contains("0: Book"));
    }

    @Test
    void processCommand_help_printsHelpText() {
        CommandHandler handler = createHandlerWithInput();
        handler.processCommand("--help",InputParser.parseQuotedInput(""));

        String output = outContent.toString();
        assertTrue(output.contains("add [Title] [Author] [Year] [Status]"));
        assertTrue(output.contains("remove [Index]"));
        assertTrue(output.contains("exit"));
    }

    @Test
    void processCommand_unknownCommand_printsError() {
        CommandHandler handler = createHandlerWithInput();
        handler.processCommand("unknown",InputParser.parseQuotedInput(""));

        String output = outContent.toString();
        assertTrue(output.contains("Invalid command"));
        assertTrue(output.contains("Unknown command"));
    }
}
