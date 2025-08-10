package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;
    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        library = new Library();
        book1 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, BookStatus.EXIST);
        book2 = new Book("1984", "George Orwell", 1949, BookStatus.BORROWED);
        book3 = new Book("Animal Farm", "George Orwell", 1945, BookStatus.BANNED);
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
    }

    @Test
    void addBook_increasesLibrarySize() {
        Book newBook = new Book("Dune", "Frank Herbert", 1965, BookStatus.EXIST);
        library.addBook(newBook);
        assertTrue(library.getBooks().contains(newBook));
        assertEquals(4, library.getBooks().size());
    }

    @Test
    void removeBook_byObject_removesCorrectBook() {
        library.removeBook(book1);
        assertFalse(library.getBooks().contains(book1));
        assertEquals(2, library.getBooks().size());
    }

    @Test
    void removeBook_byIndex_removesCorrectBook() {
        library.removeBook(0);
        assertEquals(List.of(book2, book3), library.getBooks());
    }

    @Test
    void getBook_returnsCorrectBookByIndex() {
        assertEquals(book2, library.getBook(1));
    }

    @Test
    void searchBooksByTitle_findsMatchingBooks_caseInsensitive() {
        List<Book> result1 = library.searchBooksByTitle("HOBBIT");
        List<Book> result2 = library.searchBooksByTitle("hOBbiT");
        assertEquals(List.of(book1), result1);
        assertEquals(result1, result2);
    }

    @Test
    void searchBooksByTitleIndexes_returnsCorrectIndexes() {
        List<Integer> result = library.searchBooksByTitleIndexes("Hobbit");
        assertEquals(List.of(0), result);
    }

    @Test
    void searchBooksByAuthor_findsMultipleMatches() {
        List<Book> result = library.searchBooksByAuthor("orwell");
        assertEquals(List.of(book2, book3), result);
    }

    @Test
    void searchBooksByAuthorIndexes_returnsCorrectIndexes() {
        List<Integer> result = library.searchBooksByAuthorIndexes("orwell");
        assertEquals(List.of(1, 2), result);
    }

    @Test
    void sortBooksByYear_ordersBooksAscending() {
        library.sortBooksByYear();
        assertEquals(List.of(book1, book3, book2), library.getBooks());
    }

    @Test
    void toString_withBooks_formatsCorrectly() {
        String output = library.toString();
        assertTrue(output.contains("0: " + book1.toString()));
        assertTrue(output.contains("1: " + book2.toString()));
        assertTrue(output.contains("2: " + book3.toString()));
    }

    @Test
    void toString_withNoBooks_returnsNoBookMessage() {
        Library emptyLibrary = new Library();
        assertEquals("No book to print !!!", emptyLibrary.toString());
    }
}
