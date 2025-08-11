package org.example.service;

import org.example.model.Asset;
import org.example.util.LinkedList2;

import java.util.Comparator;
import java.util.List;

public class Library {
    private final List<Asset> assets;

    public List<Asset> getAssets() {
        return assets;
    }

    public Library() {
        assets = new LinkedList2<>();
    }

    public Asset getAsset(int index) {
        return this.assets.get(index);
    }

    public void addAsset(Asset asset) {
        this.assets.add(asset);
    }

    public void removeAsset(Asset asset) {
        this.assets.remove(asset);
    }

    public void removeAsset(int index) {
        this.assets.remove(index);
    }

    public void printAssets() {
        System.out.print(this.toString());
    }

    public List<Asset> searchAssetsByTitle(String title) {
        title = title.toLowerCase();
        List<Asset> assets = new LinkedList2<>();
        for (Asset asset : this.assets) {
            if (asset.getTitle().toLowerCase().contains(title))
                assets.add(asset);

        }
        return assets;
    }

    public List<Integer> searchAssetsByTitleIndexes(String title) {
        title = title.toLowerCase();
        var bookList = this.getAssets();
        List<Integer> bookIndexes = new LinkedList2<>();
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getTitle().toLowerCase().contains(title)) {
                bookIndexes.add(i);
            }
        }
        return bookIndexes;
    }

    public List<Asset> searchAssetsByAuthor(String author) {
        author = author.toLowerCase();
        List<Asset> assets = new LinkedList2<>();
        for (Asset asset : this.assets) {
            if (asset.getAuthor().toLowerCase().contains(author))
                assets.add(asset);

        }
        return assets;
    }

    public List<Integer> searchAssetsByAuthorIndexes(String author) {
        author = author.toLowerCase();
        var bookList = this.getAssets();
        List<Integer> bookIndexes = new LinkedList2<>();
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getAuthor().toLowerCase().contains(author)) {
                bookIndexes.add(i);
            }
        }
        return bookIndexes;
    }

    public void sortAssetsByYear() {
        assets.sort(Comparator.comparingInt(Asset::getReleaseDate));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        var bookList = this.getAssets();
        if (bookList.size() == 0) {
            builder.append("No asset to print !!!");
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