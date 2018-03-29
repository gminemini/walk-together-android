package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.mission.HistoryMission;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

public class MissionModel {

    private static MissionModel instance;

    public static MissionModel getInstance() {
        if (instance == null)
            instance = new MissionModel();
        return instance;
    }

    public ArrayList<HistoryMission> getHistoryMissionArrayList(JsonObject jsonObject) {
        Type type = new TypeToken<ArrayList<HistoryMission>>() {
        }.getType();
        return new Gson().fromJson(jsonObject.get("data").getAsJsonArray(), type);
    }


}
