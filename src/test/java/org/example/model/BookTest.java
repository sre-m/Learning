package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("Animal Farm", "George Orwell", 2025, BookStatus.EXIST);
    }

    @Test
    void constructor_setsAllFieldsCorrectly() {
        assertEquals("Animal Farm", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
        assertEquals(2025, book.getReleaseDate());
        assertEquals(BookStatus.EXIST, book.getStatus());
    }

    @Test
    void setTitle_updatesTitleAndReturnsSameBook() {
        Book returned = (Book) book.setTitle("Harry Potter");
        assertSame(book, returned);
        assertEquals("Harry Potter", book.getTitle());
    }

    @Test
    void setAuthor_updatesAuthorAndReturnsSameBook() {
        Book returned = (Book) book.setAuthor("Eric Arthur Blair");
        assertSame(book, returned);
        assertEquals("Eric Arthur Blair", book.getAuthor());
    }

    @Test
    void setReleaseDate_updatesReleaseDateAndReturnsSameBook() {
        Book returned = (Book) book.setReleaseDate(1945);
        assertSame(book, returned);
        assertEquals(1945, book.getReleaseDate());
    }

    @Test
    void setStatus_updatesStatusAndReturnsSameBook() {
        Book returned = book.setStatus(BookStatus.BORROWED);
        assertSame(book, returned);
        assertEquals(BookStatus.BORROWED, book.getStatus());
    }

    @Test
    void toString_containsAllFieldValues() {
        String result = book.toString();
        assertTrue(result.contains("Animal Farm"));
        assertTrue(result.contains("George Orwell"));
        assertTrue(result.contains("2025"));
        assertTrue(result.contains(BookStatus.EXIST.name()));
    }
}

// ------------------- Tests for Book.equals() -------------------
class BookEqualsTest {

    private Book createDefaultBook() {
        return new Book("1984", "George Orwell", 1949, BookStatus.EXIST);
    }

    @Test
    void sameReference_returnsTrue() {
        Book book = createDefaultBook();
        assertTrue(book.equals(book));
    }

    @Test
    void equalValues_returnsTrue() {
        Book book1 = createDefaultBook();
        Book book2 = createDefaultBook();
        assertTrue(book1.equals(book2));
        assertTrue(book2.equals(book1));
    }

    @Test
    void differentTitle_returnsFalse() {
        Book book1 = createDefaultBook();
        Book book2 = new Book("Animal Farm", "George Orwell", 1949, BookStatus.EXIST);
        assertFalse(book1.equals(book2));
    }

    @Test
    void differentAuthor_returnsFalse() {
        Book book1 = createDefaultBook();
        Book book2 = new Book("1984", "Eric Arthur Blair", 1949, BookStatus.EXIST);
        assertFalse(book1.equals(book2));
    }

    @Test
    void differentReleaseDate_returnsFalse() {
        Book book1 = createDefaultBook();
        Book book2 = new Book("1984", "George Orwell", 1950, BookStatus.EXIST);
        assertFalse(book1.equals(book2));
    }

    @Test
    void differentStatus_returnsFalse() {
        Book book1 = createDefaultBook();
        Book book2 = new Book("1984", "George Orwell", 1949, BookStatus.BORROWED);
        assertFalse(book1.equals(book2));
    }

    @Test
    void compareWithNull_returnsFalse() {
        Book book = createDefaultBook();
        assertFalse(book.equals(null));
    }

    @Test
    void compareWithDifferentType_returnsFalse() {
        Book book = createDefaultBook();
        assertFalse(book.equals("Not a book"));
    }
}
