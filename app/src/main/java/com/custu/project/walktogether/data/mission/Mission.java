package com.custu.project.walktogether.data.mission;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mission {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("score")
    @Expose
    private Long score;
    @SerializedName("cognitiveCategory")
    @Expose
    private CognitiveCategory cognitiveCategory;
    @SerializedName("map")
    @Expose
    private Map map;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public CognitiveCategory getCognitiveCategory() {
        return cognitiveCategory;
    }

    public void setCognitiveCategory(CognitiveCategory cognitiveCategory) {
        this.cognitiveCategory = cognitiveCategory;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

}