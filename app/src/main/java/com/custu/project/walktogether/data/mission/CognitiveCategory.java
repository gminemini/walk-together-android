package com.custu.project.walktogether.data.mission;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CognitiveCategory {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("cognitiveCategoryName")
    @Expose
    private String cognitiveCategoryName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCognitiveCategoryName() {
        return cognitiveCategoryName;
    }

    public void setCognitiveCategoryName(String cognitiveCategoryName) {
        this.cognitiveCategoryName = cognitiveCategoryName;
    }

}
