package com.custu.project.walktogether.data.historyevaluation;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 4/3/2018 AD.
 */

public class EvaluationTestSingle {
    private String category;
    private String resultScore;
    private ArrayList<PatientTest> patientTest;
    private Long date;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResultScore() {
        return resultScore;
    }

    public void setResultScore(String resultScore) {
        this.resultScore = resultScore;
    }

    public ArrayList<PatientTest> getPatientTest() {
        return patientTest;
    }

    public void setPatientTest(ArrayList<PatientTest> patientTest) {
        this.patientTest = patientTest;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
