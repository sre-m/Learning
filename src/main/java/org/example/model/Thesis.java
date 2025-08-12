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
        return "Thesis," + getTitle() + "," + getAuthor() + "," + getReleaseDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        return true;
    }
}