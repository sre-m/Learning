package org.example.model;

import java.util.Objects;

public abstract class Asset implements Displayable, CSVAble{
    private String title;
    private String author;
    private int releaseDate;

    Asset(String title, String author, int releaseDate) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Asset setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Asset setAuthor(String author) {
        this.author = author;
        return this;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public Asset setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Asset)) return false;

        Asset asset = (Asset) o;
        return title.equals(asset.title) &&
                author.equals(asset.author) &&
                releaseDate==asset.releaseDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, releaseDate);
    }

    @Override
    public void display() {
        System.out.println(this.toString());
    }
}