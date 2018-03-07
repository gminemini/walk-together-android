package com.custu.project.walktogether.util;

import com.google.gson.JsonObject;

/**
 * Created by pannawatnokket on 25/2/2018 AD.
 */

public class StoreAnswerTmse {

    private static StoreAnswerTmse instance;
    private static JsonObject answer;

    public static StoreAnswerTmse getInstance() {
        if (instance == null) {
            instance = new StoreAnswerTmse();
            answer = new JsonObject();
        }
        return instance;
    }

    public void storeAnswer(String no, Long id, String answer) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("answer", answer);
        StoreAnswerTmse.answer.add(no, jsonObject);
    }

    public void storeAnswerNineteen(String no, Long id, Long idRef, String answer) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("idRef", idRef);
        jsonObject.addProperty("answer", answer);
        StoreAnswerTmse.answer.add(no, jsonObject);
    }

    public JsonObject getAllAnswer() {
        return answer;
    }
}
