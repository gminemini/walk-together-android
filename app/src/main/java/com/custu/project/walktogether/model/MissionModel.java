package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.data.mission.Mission;
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

    public ArrayList<Map> getMapArrayList(JsonObject jsonObject) {
        Type type = new TypeToken<ArrayList<Map>>() {}.getType();
        return new Gson().fromJson(jsonObject.get("data").getAsJsonArray(), type);
    }

    public ArrayList<Mission> getMissionArrayList(JsonObject jsonObject) {
        Type type = new TypeToken<ArrayList<Mission>>() {}.getType();
        return new Gson().fromJson(jsonObject.get("data").getAsJsonArray(), type);
    }

    public boolean isCorrectMission(String answer, String input) {
        return answer.equalsIgnoreCase(input);
    }
}
