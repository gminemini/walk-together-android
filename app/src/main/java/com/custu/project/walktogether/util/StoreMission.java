package com.custu.project.walktogether.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by pannawatnokket on 25/2/2018 AD.
 */

public class StoreMission {

    private static StoreMission instance;
    private static JsonObject mission;
    private static JsonArray jsonArray;

    public static StoreMission getInstance() {
        if (instance == null) {
            instance = new StoreMission();
            mission = new JsonObject();
            jsonArray = new JsonArray();
        }
        return instance;
    }

    public void storeAnswer(JsonObject mission) {
        jsonArray.add(mission);
    }

    public JsonObject getAllMission(Long mapId, String route, Long time, int distance) {
        mission.add("mission", jsonArray);
        mission.addProperty("mapId", mapId);
        mission.addProperty("route", route);
        mission.addProperty("time", time);
        mission.addProperty("distance", distance);
        jsonArray = new JsonArray();
        return mission;
    }

    public void destroyMission() {
        jsonArray = new JsonArray();
    }

}
