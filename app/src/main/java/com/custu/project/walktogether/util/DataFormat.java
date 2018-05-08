package com.custu.project.walktogether.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by pannawatnokket on 11/3/2018 AD.
 */

public class DataFormat {

    private static DataFormat instance;
    private static Gson gson;

    public static DataFormat getInstance() {
        if (instance == null) {
            instance = new DataFormat();
        }
        return instance;
    }

    public String validateData(String string) {
        return string == null ? " " : string;
    }

    public Gson getGsonParser() {
        if (null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }

    public String addDoubleCode(String question) {
        return "\"" + question + "\"";
    }
}
