package com.santossingh.capstoneproject.Models.Google;

import java.util.List;

/*
 * Created by santoshsingh on 25/12/16.
 */

public class VolumeInfo {
    private String title;
    private List<String> authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private List<IndustryIdentifiers> industryIdentifiers;
    private ReadingModes readingModes;
    private int pageCount;
    private String printType;
    private List<String> categories;
    private float averageRating;
    private int ratingsCount;
    private String maturityRating;
    private boolean allowAnonLogging;
    private String contentVersion;
    private ImageLinks imageLinks;
    private String language;
    private String previewLink;
    private String infoLink;
    private String canonicalVolumeLink;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return this.authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return this.publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<IndustryIdentifiers> getIndustryIdentifiers() {
        return this.industryIdentifiers;
    }

    public void setIndustryIdentifiers(List<IndustryIdentifiers> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public ReadingModes getReadingModes() {
        return this.readingModes;
    }

    public void setReadingModes(ReadingModes readingModes) {
        this.readingModes = readingModes;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getPrintType() {
        return this.printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public List<String> getCategories() {
        return this.categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public float getAverageRating() {
        return this.averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingsCount() {
        return this.ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public String getMaturityRating() {
        return this.maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public boolean getAllowAnonLogging() {
        return this.allowAnonLogging;
    }

    public void setAllowAnonLogging(boolean allowAnonLogging) {
        this.allowAnonLogging = allowAnonLogging;
    }

    public String getContentVersion() {
        return this.contentVersion;
    }

    public void setContentVersion(String contentVersion) {
        this.contentVersion = contentVersion;
    }

    public ImageLinks getImageLinks() {
        return this.imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPreviewLink() {
        return this.previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return this.infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getCanonicalVolumeLink() {
        return this.canonicalVolumeLink;
    }

    public void setCanonicalVolumeLink(String canonicalVolumeLink) {
        this.canonicalVolumeLink = canonicalVolumeLink;
    }
}
