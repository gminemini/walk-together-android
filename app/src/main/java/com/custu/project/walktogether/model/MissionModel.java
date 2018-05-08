package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.mission.AnswerMission;
import com.custu.project.walktogether.data.mission.HistoryMission;
import com.custu.project.walktogether.data.mission.Map;
import com.custu.project.walktogether.data.mission.Mission;
import com.custu.project.walktogether.data.mission.MissionDetail;
import com.custu.project.walktogether.data.mission.PatientMissionList;
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
}
