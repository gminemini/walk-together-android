package com.custu.project.walktogether.model;

import android.content.Context;

import com.custu.project.walktogether.data.Evaluation.Answer;
import com.custu.project.walktogether.data.Evaluation.NumberQuestion;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.data.Evaluation.Tmse;
import com.custu.project.walktogether.util.UserManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pannawatnokket on 1/2/2018 AD.
 */

public class EvaluationModel {
    private static EvaluationModel instance;

    public static EvaluationModel getInstance() {
        if (instance == null)
            instance = new EvaluationModel();
        return instance;
    }

    public ArrayList<Tmse> getTmse(JsonObject data) {
        ArrayList<Tmse> tmseArrayList = new ArrayList<>();
        ArrayList<Answer> answerArrayList;
        ArrayList<NumberQuestion> numberQuestionArrayList;
        JsonArray jsonArray = data.getAsJsonArray("data");

        for (int i = 0; i < jsonArray.size(); i++) {
            Tmse tmse = new Tmse();
            numberQuestionArrayList = new ArrayList<>();
            JsonArray questionJsonArray = jsonArray.get(i).getAsJsonObject().getAsJsonArray("questions");
            for (int j = 0; j < questionJsonArray.size(); j++) {
                NumberQuestion numberQuestion = new NumberQuestion();
                JsonArray answerJsonArray = questionJsonArray.get(j).getAsJsonObject().getAsJsonArray("answer");
                answerArrayList = new ArrayList<>();
                for (int k = 0; k < answerJsonArray.size(); k++) {
                    Answer answer = new Gson().fromJson(answerJsonArray.get(k), Answer.class);
                    answerArrayList.add(answer);
                }

                Question question = new Gson().fromJson(questionJsonArray.get(j).getAsJsonObject().get("question"), Question.class);
                numberQuestion.setAnswerArrayList(answerArrayList);
                numberQuestion.setQuestion(question);
                numberQuestion.setNo(questionJsonArray.get(j).getAsJsonObject().get("no").getAsString());
                numberQuestionArrayList.add(numberQuestion);
            }

            tmse.setNumberQuestions(numberQuestionArrayList);
            tmse.setCategory(jsonArray.get(i).getAsJsonObject().get("category").getAsString());
            tmseArrayList.add(tmse);

        }
        return tmseArrayList;
    }

    public NumberQuestion getEvaluationByNumber(String number, Context context) {
        ArrayList<Tmse> tmseArrayList = UserManager.getInstance(context).getTMSE();
        for (Tmse tmse : tmseArrayList) {
            for (NumberQuestion numberQuestion : tmse.getNumberQuestions()) {
                if (numberQuestion.getNo().equalsIgnoreCase(number)) {
                    return numberQuestion;
                }
            }
        }
        return null;
    }

    public String getDummyChoiceCheckBox() {
        Random random = new Random();
        int ran = random.nextInt(3) + 1;
        switch (ran) {
            case 1:
                return ",เตียงนอน,พัดลม,ปากกา";
            case 2:
                return ",หลอดไฟ,ดินสอ,ผ้าห่ม";
            case 3:
                return ",ยางลบ,ดอกไม้,ก้อนหิน";
            default:
                return ",ยางลบ,ดอกไม้,ก้อนหิน";
        }
    }

    public String[] getDummyChoiceRadioBox() {
        String[] strings = new String[3];
        strings[0] = "ลุงพายายไปตลาด";
        strings[1] = "ยายพาหลานไปตลาด";
        strings[2] = "หลานพาลุงไปเที่ยว";
        return strings;
    }
}
