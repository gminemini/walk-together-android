package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.mission.AnswerMission;
import com.custu.project.walktogether.data.mission.HistoryMission;
import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.data.mission.Mission;
import com.custu.project.walktogether.data.mission.MissionDetail;
import com.custu.project.walktogether.data.mission.PatientGame;
import com.custu.project.walktogether.data.mission.PatientMissionList;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
        Type type = new TypeToken<ArrayList<Map>>() {
        }.getType();
        return new Gson().fromJson(jsonObject.get("data").getAsJsonArray(), type);
    }

    public ArrayList<Mission> getMissionArrayList(JsonObject jsonObject) {
        Type type = new TypeToken<ArrayList<Mission>>() {
        }.getType();
        return new Gson().fromJson(jsonObject.get("data").getAsJsonArray(), type);
    }

    public ArrayList<HistoryMission> getHistoryMissionArrayList(JsonObject jsonObject) {
        Type type = new TypeToken<ArrayList<HistoryMission>>() {
        }.getType();
        return new Gson().fromJson(jsonObject.get("data").getAsJsonArray(), type);
    }

    public ArrayList<PatientMissionList> getPatientMissionListArrayList(String data) {
        Type type = new TypeToken<ArrayList<PatientMissionList>>() {
        }.getType();
        return new Gson().fromJson(data, type);
    }

    public ArrayList<AnswerMission> getAnswerMissions(JsonObject jsonObject) {
        Type type = new TypeToken<ArrayList<AnswerMission>>() {
        }.getType();
        return new Gson().fromJson(jsonObject.get("data").getAsJsonArray(), type);
    }

    public MissionDetail getMissionDetail(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject.get("data").getAsJsonObject(), MissionDetail.class);
    }

    public boolean isCorrectMission(String answer, String input) {
        return answer.equalsIgnoreCase(input);
    }

    public ArrayList<LatLng> getRouteMissions(String route) {
        Type type = new TypeToken<ArrayList<LatLng>>() {
        }.getType();
        return new Gson().fromJson(route, type);
    }

    public PatientGame getPatientGame(JsonObject jsonObject) {
        return new Gson().fromJson(jsonObject.get("data").getAsJsonObject().get("patientGame").getAsJsonObject(), PatientGame.class);
    }

    public JsonObject mappingMission(LatLng origin, List<LatLng> wayPoints, LatLng destination) {
        JsonObject result = new JsonObject();
        JsonObject originJsonObject = new JsonObject();
        JsonObject destinationJsonObject = new JsonObject();
        JsonArray wayPointsArray = new JsonArray();

        originJsonObject.addProperty("latitude", origin.latitude);
        originJsonObject.addProperty("longitude", origin.longitude);

        destinationJsonObject.addProperty("latitude", destination.latitude);
        destinationJsonObject.addProperty("longitude", destination.longitude);

        for (LatLng latLng : wayPoints) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("latitude", latLng.latitude);
            jsonObject.addProperty("longitude", latLng.longitude);
            wayPointsArray.add(jsonObject);
        }

        result.add("origin", originJsonObject);
        result.add("wayPoint", wayPointsArray);
        result.add("destination", destinationJsonObject);
        return result;
    }
}
