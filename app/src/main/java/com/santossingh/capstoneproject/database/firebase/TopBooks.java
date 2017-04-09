package com.santossingh.capstoneproject.database.firebase;


@SuppressWarnings("ALL")
public class TopBooks {
    private String id;
    private String title;
    private String author;
    private String image;
    private String publishedDate;
    private String price;
    private String buyLink;
    private String reviewLink;
    private String description;
    private String key;

    public TopBooks() {
    }

    public TopBooks(String id, String title, String author, String image, String publishedDate, String price, String buyLink, String reviewLink, String description) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.buyLink = buyLink;
        this.image = image;
        this.price = price;
        this.publishedDate = publishedDate;
        this.reviewLink = reviewLink;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getReviewLink() {
        return reviewLink;
    }

    public void setReviewLink(String reviewLink) {
        this.reviewLink = reviewLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
