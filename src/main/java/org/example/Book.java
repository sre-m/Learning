package org.example;

public class Book {
    private String title;
    private String author;
    private int releaseDate;
    private BookStatus status;

    public Book(String title, String author, int releaseDate, BookStatus status) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public Book setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Book setStatus(BookStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Book [title: " + title +
                ", author: " + author +
                ", releaseDate: " + releaseDate +
                ", status: " + status + "]";
    }
}