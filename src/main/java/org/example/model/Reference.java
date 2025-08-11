package org.example.model;

public class Reference extends Asset {
    public Reference(String title, String author, int releaseDate) {
        super(title, author, releaseDate);
    }

    @Override
    public String toString() {
        return "Reference [title: " + super.getTitle() +
                ", author: " + super.getAuthor() +
                ", releaseDate: " + super.getReleaseDate() + "]";
    }

    @Override
    public String toCSV() {
        return "Reference," + getTitle() + "," + getAuthor() + "," + getReleaseDate();
    }
}
