package com.custu.project.walktogether.data.mission;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientGame {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("resultScore")
    @Expose
    private Integer resultScore;
    @SerializedName("map")
    @Expose
    private Map map;
    @SerializedName("patientMissionList")
    @Expose
    private List<PatientMissionList> patientMissionList = null;
    @SerializedName("route")
    @Expose
    private String route;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResultScore() {
        return resultScore;
    }

    public void setResultScore(Integer resultScore) {
        this.resultScore = resultScore;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public List<PatientMissionList> getPatientMissionList() {
        return patientMissionList;
    }

    public void setPatientMissionList(List<PatientMissionList> patientMissionList) {
        this.patientMissionList = patientMissionList;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}
