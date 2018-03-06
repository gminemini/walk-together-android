package com.custu.project.walktogether.data.mission;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by pannawatnokket on 6/3/2018 AD.
 */

public class HistoryMission {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("score")
    @Expose
    private Long score;
    @SerializedName("patientGame")
    @Expose
    private PatientGame patientGame;
    @SerializedName("historyDate")
    @Expose
    private String historyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public PatientGame getPatientGame() {
        return patientGame;
    }

    public void setPatientGame(PatientGame patientGame) {
        this.patientGame = patientGame;
    }

    public String getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(String historyDate) {
        this.historyDate = historyDate;
    }

}
