package com.custu.project.walktogether.model;

import com.custu.project.walktogether.data.Evaluation.Answer;
import com.custu.project.walktogether.data.Evaluation.NumberQuestion;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.data.Evaluation.Tmse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

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

}
