package com.custu.project.walktogether.data.historyevaluation;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 3/3/2018 AD.
 */

public class EvaluationTest {

    private Long id;
    private ArrayList<PatientTest> patientTests;
    private Long testDate;
    private String resultScore;
    private String frequencyPatient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<PatientTest> getPatientTests() {
        return patientTests;
    }

    public void setPatientTests(ArrayList<PatientTest> patientTests) {
        this.patientTests = patientTests;
    }

    public Long getTestDate() {
        return testDate;
    }

    public void setTestDate(Long testDate) {
        this.testDate = testDate;
    }

    public String getResultScore() {
        return resultScore;
    }

    public void setResultScore(String resultScore) {
        this.resultScore = resultScore;
    }

    public String getFrequencyPatient() {
        return frequencyPatient;
    }

    public void setFrequencyPatient(String frequencyPatient) {
        this.frequencyPatient = frequencyPatient;
    }
}
