package com.santossingh.capstoneproject.Models.Google;

/**
 * Created by santoshsingh on 25/12/16.
 */

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
