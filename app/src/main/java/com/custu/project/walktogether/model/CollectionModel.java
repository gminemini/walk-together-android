package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.collection.Collection;
import com.custu.project.walktogether.data.collection.Reward;
import com.custu.project.walktogether.data.mission.HistoryMission;
import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.data.mission.Mission;
import com.custu.project.walktogether.data.mission.PatientMissionList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

    public Reward getReward(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject.get("data"), Reward.class);
    }
}
