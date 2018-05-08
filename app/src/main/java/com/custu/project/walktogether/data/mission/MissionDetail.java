package com.custu.project.walktogether.data.mission;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissionDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("cognitiveCategory")
    @Expose
    private CognitiveCategory cognitiveCategory;
    @SerializedName("answerMissions")
    @Expose
    private List<AnswerMission> answerMissions = null;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public CognitiveCategory getCognitiveCategory() {
        return cognitiveCategory;
    }

    public void setCognitiveCategory(CognitiveCategory cognitiveCategory) {
        this.cognitiveCategory = cognitiveCategory;
    }

    public List<AnswerMission> getAnswerMissions() {
        return answerMissions;
    }

    public void setAnswerMissions(List<AnswerMission> answerMissions) {
        this.answerMissions = answerMissions;
    }

}

