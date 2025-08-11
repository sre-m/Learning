package org.example.service;

import org.example.model.Asset;
import org.example.model.Book;
import org.example.model.BookStatus;
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
       var book1 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, BookStatus.EXIST);
       var book2 = new Book("1984", "George Orwell", 1949, BookStatus.BORROWED);
       var book3 = new Book("Animal Farm", "George Orwell", 1945, BookStatus.BANNED);
        library.addAsset(book1);
        library.addAsset(book2);
        library.addAsset(book3);
    }

    @Test
    void addBook_increasesLibrarySize() {
        Book newBook = new Book("Dune", "Frank Herbert", 1965, BookStatus.EXIST);
        library.addAsset(newBook);
        assertTrue(library.getAssets().contains(newBook));
        assertEquals(4, library.getAssets().size());
    }

    @Test
    void removeBook_byObject_removesCorrectBook() {
        library.removeAsset(book1);
        Book b1 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, BookStatus.EXIST);
        library.removeAsset(b1);
        assertFalse(library.getAssets().contains(b1));
        assertEquals(2, library.getAssets().size());
    }

    @Test
    void removeBook_byIndex_removesCorrectBook() {
        library.removeAsset(0);
        assertEquals(List.of(book2, book3), library.getAssets());
    }

    @Test
    void getBook_returnsCorrectBookByIndex() {
        assertEquals(book2, library.getAsset(1));
    }

    @Test
    void searchBooksByTitle_findsMatchingBooks_caseInsensitive() {
        List<Asset> result1 = library.searchAssetsByTitle("HOBBIT");
        List<Asset> result2 = library.searchAssetsByTitle("hOBbiT");
        assertEquals(List.of(book1), result1);
        assertEquals(result1, result2);
    }

    @Test
    void searchBooksByTitleIndexes_returnsCorrectIndexes() {
        List<Integer> result = library.searchAssetsByTitleIndexes("Hobbit");
        assertEquals(List.of(0), result);
    }

    @Test
    void searchBooksByAuthor_findsMultipleMatches() {
        List<Asset> result = library.searchAssetsByAuthor("orwell");
        assertEquals(List.of(book2, book3), result);
    }

    @Test
    void searchBooksByAuthorIndexes_returnsCorrectIndexes() {
        List<Integer> result = library.searchAssetsByAuthorIndexes("orwell");
        assertEquals(List.of(1, 2), result);
    }

    @Test
    void sortBooksByYear_ordersBooksAscending() {
        library.sortAssetsByYear();
        assertEquals(List.of(book1, book3, book2), library.getAssets());
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
        assertEquals("No asset to print !!!", emptyLibrary.toString());
    }
}
