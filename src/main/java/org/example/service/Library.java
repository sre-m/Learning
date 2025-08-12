package org.example.service;

import org.example.model.Asset;
import org.example.util.FieldHandler;
import org.example.util.LinkedList2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Library {
    private final List<Asset> assets;

    private List<Asset> searchedAssets;

    public List<Asset> getAssets() {
        return assets;
    }

    public Library() {
        assets = new LinkedList2<>();
        searchedAssets = new LinkedList2<>();
    }

    public Asset getAsset(int index) {
        try {
            return this.searchedAssets.get(index);
        }catch (Exception e){
            return null;
        }
    }

    public List<Asset> getSearchedAssets() {
        return searchedAssets;
    }

    public void addAsset(Asset asset) {
        this.assets.add(asset);
    }

    public void removeAsset(Asset asset) {
        this.assets.remove(asset);
        this.searchedAssets.remove(asset);
    }

    public void removeAsset(int index) {
        Asset asset = this.searchedAssets.get(index);
        this.searchedAssets.remove(asset);
        this.assets.remove(asset);
    }

    public void printSearchedAssets() {
        StringBuilder builder = new StringBuilder();
        var bookList = getSearchedAssets();
        if (bookList.isEmpty()) {
            builder.append("No asset to print !!!");
        } else {
            for (int i = 0; i < bookList.size(); i++) {
                builder.append(i);
                builder.append(": ");
                builder.append(bookList.get(i));
                builder.append("\n");
            }
        }
        System.out.println(builder.toString());
    }

    public void searchAssets(List<String> assetTypes, List<String> fields, String term) {
        fillSearchedAssets();
        filterByTypes(assetTypes);
        filterByFields(fields, term);
    }

    private void filterByFields(List<String> fields, String term) {
        searchedAssets = FieldHandler.searchFields(getSearchedAssets(), fields, term);
    }

    private void filterByTypes(List<String> assetTypes) {
        searchedAssets = getSearchedAssets().stream().filter((asset) -> assetTypes.contains(asset.getClass().getSimpleName())).toList();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (assets.isEmpty()) {
            builder.append("No asset to print !!!");
        }
        else {
            for (Asset asset : assets) {
                builder.append(asset.toString());
                builder.append('\n');
            }
        }
        return builder.toString();
    }

    public void fillSearchedAssets() {
        searchedAssets = new LinkedList2<>();
        for( Asset asset : assets) {
            searchedAssets.add(asset);
        }
    }
}