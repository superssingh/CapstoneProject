package com.santossingh.capstoneproject.Google.Models;


public class AccessInfo {
    private String country;
    private String viewability;
    private boolean embeddable;
    private boolean publicDomain;
    private String textToSpeechPermission;
    private Epub epub;
    private Pdf pdf;
    private String webReaderLink;
    private String accessViewStatus;
    private boolean quoteSharingAllowed;

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getViewability() {
        return this.viewability;
    }

    public void setViewability(String viewability) {
        this.viewability = viewability;
    }

    public boolean getEmbeddable() {
        return this.embeddable;
    }

    public void setEmbeddable(boolean embeddable) {
        this.embeddable = embeddable;
    }

    public boolean getPublicDomain() {
        return this.publicDomain;
    }

    public void setPublicDomain(boolean publicDomain) {
        this.publicDomain = publicDomain;
    }

    public String getTextToSpeechPermission() {
        return this.textToSpeechPermission;
    }

    public void setTextToSpeechPermission(String textToSpeechPermission) {
        this.textToSpeechPermission = textToSpeechPermission;
    }

    public Epub getEpub() {
        return this.epub;
    }

    public void setEpub(Epub epub) {
        this.epub = epub;
    }

    public Pdf getPdf() {
        return this.pdf;
    }

    public void setPdf(Pdf pdf) {
        this.pdf = pdf;
    }

    public String getWebReaderLink() {
        return this.webReaderLink;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public String getAccessViewStatus() {
        return this.accessViewStatus;
    }

    public void setAccessViewStatus(String accessViewStatus) {
        this.accessViewStatus = accessViewStatus;
    }

    public boolean getQuoteSharingAllowed() {
        return this.quoteSharingAllowed;
    }

    public void setQuoteSharingAllowed(boolean quoteSharingAllowed) {
        this.quoteSharingAllowed = quoteSharingAllowed;
    }
}
