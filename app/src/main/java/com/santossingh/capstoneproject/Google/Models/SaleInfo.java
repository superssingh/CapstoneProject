package com.santossingh.capstoneproject.Google.Models;


public class SaleInfo {
    private String country;
    private String saleability;
    private boolean isEbook;
    private String buyLink;


    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public boolean isEbook() {
        return isEbook;
    }

    public void setEbook(boolean ebook) {
        isEbook = ebook;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSaleability() {
        return this.saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public boolean getIsEbook() {
        return this.isEbook;
    }

    public void setIsEbook(boolean isEbook) {
        this.isEbook = isEbook;
    }
}
