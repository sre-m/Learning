package org.example.model;

public class Thesis extends Asset {
    public Thesis(String title, String author, int releaseDate) {
        super(title, author, releaseDate);
    }

    @Override
    public String toString() {
        return "Thesis [title: " + super.getTitle() +
                ", author: " + super.getAuthor() +
                ", releaseDate: " + super.getReleaseDate() + "]";
    }

    @Override
    public String toCSV() {
        return "Reference," + getTitle() + "," + getAuthor() + "," + getReleaseDate();
    }
}
