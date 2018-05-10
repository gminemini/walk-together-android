package com.custu.project.walktogether.data.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reward {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rewardName")
    @Expose
    private String rewardName;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
