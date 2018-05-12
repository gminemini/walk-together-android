package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.collection.Album;
import com.custu.project.walktogether.data.collection.Collection;
import com.custu.project.walktogether.data.collection.Reward;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

public class CollectionModel {

    private static CollectionModel instance;

    public static CollectionModel getInstance() {
        if (instance == null)
            instance = new CollectionModel();
        return instance;
    }

    public ArrayList<Collection> getCollectionArrayList(JsonObject jsonObject) {
        Type type = new TypeToken<ArrayList<Collection>>() {
        }.getType();
        return new Gson().fromJson(jsonObject.get("data").getAsJsonArray(), type);
    }

    public ArrayList<Album> getAlbumArrayList(JsonObject jsonObject) {
        Type type = new TypeToken<ArrayList<Album>>() {
        }.getType();
        return new Gson().fromJson(jsonObject.get("data").getAsJsonArray(), type);
    }

    public String getCollectionString(List<Collection> collections) {
        return new Gson().toJson(collections);
    }

    public List<Collection> getCollections(String collection) {
        Type type = new TypeToken<ArrayList<Collection>>() {
        }.getType();
        return new Gson().fromJson(collection, type);
    }

    public Reward getReward(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject.get("data"), Reward.class);
    }
}
