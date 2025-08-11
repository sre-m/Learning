package org.example.model;

public class Book extends Asset {
    private BookStatus status;

    public Book(String title, String author, int releaseDate, BookStatus status) {
        super(title, author, releaseDate);
        this.status = status;
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
        return "Book [title: " + super.getTitle() +
                ", author: " + super.getAuthor() +
                ", releaseDate: " + super.getReleaseDate() +
                ", status: " + status + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return book.getStatus().equals(status);
    }

    @Override
    public String toCSV() {
        return "Book," + getTitle() + "," + getAuthor() + "," + getReleaseDate() + "," + status;
    }
}