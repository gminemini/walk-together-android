package com.custu.project.walktogether.data.mission;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientMissionList {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("mission")
    @Expose
    private MissionDetail mission;
    @SerializedName("position")
    @Expose
    private Position position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public MissionDetail getMission() {
        return mission;
    }

    public void MissionDetail(MissionDetail mission) {
        this.mission = mission;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
