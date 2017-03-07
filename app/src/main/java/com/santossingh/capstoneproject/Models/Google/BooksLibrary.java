package com.santossingh.capstoneproject.Models.Google;

/**
 * Created by santoshsingh on 24/12/16.
 */

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
