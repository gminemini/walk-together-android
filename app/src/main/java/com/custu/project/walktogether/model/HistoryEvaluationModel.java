package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.historyevaluation.EvaluationTest;
import com.custu.project.walktogether.data.historyevaluation.EvaluationTestSingle;
import com.custu.project.walktogether.data.historyevaluation.HistoryEvaluation;
import com.custu.project.walktogether.data.historyevaluation.PatientTest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by pannawatnokket on 3/3/2018 AD.
 */

public class HistoryEvaluationModel {

    private static HistoryEvaluationModel instance;

    public static HistoryEvaluationModel getInstance() {
        if (instance == null)
            instance = new HistoryEvaluationModel();
        return instance;
    }

    public ArrayList<HistoryEvaluation> getHistoryEvaluations(JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        ArrayList<HistoryEvaluation> historyEvaluations = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            ArrayList<PatientTest> patientTests = new ArrayList<>();
            JsonObject evaluationTestJsonObject = jsonArray.get(i).getAsJsonObject().getAsJsonObject("evaluationTest");

            HistoryEvaluation historyEvaluation = new HistoryEvaluation();
            EvaluationTest evaluationTest = new Gson().fromJson(evaluationTestJsonObject, EvaluationTest.class);

            for (int j = 0; j < evaluationTestJsonObject.get("patientTests").getAsJsonArray().size(); j++) {
                patientTests.add(new Gson().fromJson(evaluationTestJsonObject.get("patientTests").getAsJsonArray().get(j).getAsJsonObject(), PatientTest.class));
            }
            evaluationTest.setPatientTests(patientTests);
            historyEvaluation.setEvaluationTest(evaluationTest);

            historyEvaluations.add(historyEvaluation);
        }
        return historyEvaluations;
    }

    public EvaluationTestSingle getEvaluationTestByCategory(String category, EvaluationTest input) {
        int resultScore = 0;
        EvaluationTestSingle evaluationTestSingle = new EvaluationTestSingle();
        ArrayList<PatientTest> patientTestArrayList = new ArrayList<>();
        for (int i = 0; i < input.getPatientTests().size(); i++) {
            if (input.getPatientTests().get(i).getEvaluationCategory().equalsIgnoreCase(category)) {
                patientTestArrayList.add(input.getPatientTests().get(i));
                resultScore += Integer.parseInt(input.getPatientTests().get(i).getScore());
            }
        }
        evaluationTestSingle.setPatientTest(patientTestArrayList);
        evaluationTestSingle.setResultScore(String.valueOf(resultScore));
        evaluationTestSingle.setCategory(category);
        evaluationTestSingle.setDate(input.getTestDate());
        return evaluationTestSingle;
    }
}
