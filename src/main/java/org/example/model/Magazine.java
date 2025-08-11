package org.example.model;

public class Magazine extends Asset {
    private String publisher;

    public Magazine(String title, String author, int releaseDate, String publisher) {
        super(title, author, releaseDate);
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Magazine [title: " + super.getTitle() +
                ", author: " + super.getAuthor() +
                ", releaseDate: " + super.getReleaseDate() +
                ", publisher: " + getPublisher() + "]";
    }

    @Override
    public String toCSV() {
        return "Reference," + getTitle() + "," + getAuthor() + "," + getReleaseDate() + "," + getPublisher();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Magazine magazine = (Magazine) o;
        return magazine.getPublisher().equals(publisher);
    }
}
