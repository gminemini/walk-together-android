package com.custu.project.walktogether.data.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collection {

    @SerializedName("reward")
    @Expose
    private Reward reward;
    @SerializedName("isLock")
    @Expose
    private Boolean isLock;
    @SerializedName("isReceive")
    @Expose
    private Boolean isReceive;

    public Reward getReward() {
        return reward;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }

    public Boolean getIsLock() {
        return isLock;
    }

    public void setIsLock(Boolean isLock) {
        this.isLock = isLock;
    }

    public Boolean getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Boolean isReceive) {
        this.isReceive = isReceive;
    }

}
