package org.example;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.List;

public class Library {
    private final List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public Library() {
        books = new LinkedList<>();
    }

    public Book getBook(int index) {
        return this.books.get(index);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    public void removeBook(int index) {
        this.books.remove(index);
    }

    public void printBooks() {
        System.out.print(this.toString());
    }

    public List<Book> searchBooksByTitle(String title) {
        title = title.toLowerCase();
        List<Book> books = new LinkedList<>();
        for (Book book : this.books) {
            if (book.getTitle().toLowerCase().contains(title))
                books.add(book);

        }
        return books;
    }

    public List<Integer> searchBooksByTitleIndexes(String title) {
        title = title.toLowerCase();
        var bookList = this.getBooks();
        List<Integer> bookIndexes = new LinkedList<>();
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().toLowerCase().contains(title)) {
                bookIndexes.add(i);
            }
        }
        return bookIndexes;
    }

    public List<Book> searchBooksByAuthor(String author) {
        author = author.toLowerCase();
        List<Book> books = new LinkedList<>();
        for (Book book : this.books) {
            if (book.getAuthor().toLowerCase().contains(author))
                books.add(book);

        }
        return books;
    }

    public List<Integer> searchBooksByAuthorIndexes(String author) {
        author = author.toLowerCase();
        var bookList = this.getBooks();
        List<Integer> bookIndexes = new LinkedList<>();
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getAuthor().toLowerCase().contains(author)) {
                bookIndexes.add(i);
            }
        }
        return bookIndexes;
    }

    public void sortBooksByYear() {
        books.sort(Comparator.comparingInt(Book::getReleaseDate));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        var bookList = this.getBooks();
        if (bookList.size() == 0) {
            builder.append("No book to print !!!");
        } else {
            for (int i = 0; i < bookList.size(); i++) {
                builder.append(i);
                builder.append(": ");
                builder.append(bookList.get(i));
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}