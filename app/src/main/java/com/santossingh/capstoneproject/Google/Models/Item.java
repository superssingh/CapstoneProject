package com.santossingh.capstoneproject.Google.Models;

import android.os.Parcel;
import android.os.Parcelable;


public class Item implements Parcelable {
    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    private String kind;
    private String id;
    private String etag;
    private String selfLink;
    private VolumeInfo volumeInfo;
    private SaleInfo saleInfo;
    private AccessInfo accessInfo;
    private SearchInfo searchInfo;

    public Item(String kind, String id, String etag, String selfLink, VolumeInfo volumeInfo, SaleInfo saleInfo, AccessInfo accessInfo, SearchInfo searchInfo) {
        this.kind = kind;
        this.id = id;
        this.etag = etag;
        this.selfLink = selfLink;
        this.volumeInfo = volumeInfo;
        this.saleInfo = saleInfo;
        this.accessInfo = accessInfo;
        this.searchInfo = searchInfo;
    }

    protected Item(Parcel in) {
        kind = in.readString();
        id = in.readString();
        etag = in.readString();
        selfLink = in.readString();
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEtag() {
        return this.etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getSelfLink() {
        return this.selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public VolumeInfo getVolumeInfo() {
        return this.volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public SaleInfo getSaleInfo() {
        return this.saleInfo;
    }

    public void setSaleInfo(SaleInfo saleInfo) {
        this.saleInfo = saleInfo;
    }

    public AccessInfo getAccessInfo() {
        return this.accessInfo;
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    public SearchInfo getSearchInfo() {
        return this.searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(kind);
        parcel.writeString(id);
        parcel.writeString(etag);
        parcel.writeString(selfLink);
    }
}
