package com.custu.project.walktogether.data.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album {
    @SerializedName("isLock")
    @Expose
    private Boolean isLock;
    @SerializedName("albumName")
    @Expose
    private String albumName;
    @SerializedName("collection")
    @Expose
    private List<Collection> collectionList;
    @SerializedName("previewImage")
    @Expose
    private List<Collection> previewImage;

    public Boolean getLock() {
        return isLock;
    }

    public void setLock(Boolean lock) {
        isLock = lock;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<Collection> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<Collection> collectionList) {
        this.collectionList = collectionList;
    }

    public List<Collection> getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(List<Collection> previewImage) {
        this.previewImage = previewImage;
    }
}
