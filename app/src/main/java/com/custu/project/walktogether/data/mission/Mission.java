package com.custu.project.walktogether.data.mission;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mission {

    @SerializedName("position")
    @Expose
    private Position position;
    @SerializedName("missionDetail")
    @Expose
    private MissionDetail missionDetail;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public MissionDetail getMissionDetail() {
        return missionDetail;
    }

    public void setMissionDetail(MissionDetail missionDetail) {
        this.missionDetail = missionDetail;
    }

}
