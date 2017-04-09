package com.santossingh.capstoneproject.google.models;


public class ReadingModes {
    private boolean text;
    private boolean image;

    public boolean getText() {
        return this.text;
    }

    public void setText(boolean text) {
        this.text = text;
    }

    public boolean getImage() {
        return this.image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }
}
