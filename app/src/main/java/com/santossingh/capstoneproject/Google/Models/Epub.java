package com.santossingh.capstoneproject.Google.Models;

/**
 * Created by santoshsingh on 25/12/16.
 */

public class Epub {
    private boolean isAvailable;
    private String downloadLink;

    public boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getDownloadLink() {
        return this.downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}