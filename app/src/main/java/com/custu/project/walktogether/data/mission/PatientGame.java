package com.custu.project.walktogether.data.mission;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientGame {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("resultScore")
    @Expose
    private Long resultScore;
    @SerializedName("map")
    @Expose
    private Map map;
    @SerializedName("patientMissionList")
    @Expose
    private List<PatientMission> patientMissionList = null;
    @SerializedName("route")
    @Expose
    private String route;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResultScore() {
        return resultScore;
    }

    public void setResultScore(Long resultScore) {
        this.resultScore = resultScore;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List<PatientMission> getPatientMissionList() {
        return patientMissionList;
    }

    public void setPatientMissionList(List<PatientMission> patientMissionList) {
        this.patientMissionList = patientMissionList;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}