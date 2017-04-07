package com.santossingh.capstoneproject.Google.Models;

public class BooksLibrary {
    private String kind;
    private int totalItems;
    private Item[] items;

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return this.totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public Item[] getItems() {
        return this.items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
