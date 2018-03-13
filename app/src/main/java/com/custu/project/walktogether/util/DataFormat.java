package com.custu.project.walktogether.util;

/**
 * Created by pannawatnokket on 11/3/2018 AD.
 */

public class DataFormat {

    private static DataFormat instance;

    public static DataFormat getInstance() {
        if (instance == null) {
            instance = new DataFormat();
        }
        return instance;
    }

    public String validateData(String string) {
        return string == null ? "-" : string;
    }
}
