package com.custu.project.walktogether.util;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pannawatnokket on 11/2/2018 AD.
 */

public class InitSpinnerDob {
    private static InitSpinnerDob instance;

    public static InitSpinnerDob getInstance() {
        if (instance == null)
            instance = new InitSpinnerDob();
        return instance;
    }

    public ArrayList<String> createSpinnerDay() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 32; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    public ArrayList<String> createSpinnerMonth() {
        ArrayList<String> list = new ArrayList<>();
        list.add("มกราคม");
        list.add("กุมภาพันธ์");
        list.add("มีนาคม");
        list.add("เมษายน");
        list.add("พฤษภาคม");
        list.add("มิถุนายน");
        list.add("กรกฎาคม");
        list.add("สิงหาคม");
        list.add("กันยายน");
        list.add("ตุลาคม");
        list.add("พฤศจิกายน");
        list.add("ธันวาคม");
        return list;
    }

    public ArrayList<String> createSpinnerYear() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 2480; i < 2561; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    public int getIndexDay(String input) {
        ArrayList<String> list = createSpinnerDay();
        String day = input.split(" ")[0];
        for (int i = 0; i < list.size(); i++) {
            if (day.equalsIgnoreCase(list.get(i)))
                return i;
        }
        return 0;
    }

    public int getIndexMonth(String input) {
        ArrayList<String> list = createSpinnerMonth();
        String month = input.split(" ")[1];
        for (int i = 0; i < list.size(); i++) {
            if (month.equalsIgnoreCase(list.get(i)))
                return i;
        }
        return 0;
    }

    public int getIndexYear(String input) {
        ArrayList<String> list = createSpinnerYear();
        String year = input.split(" ")[2];
        for (int i = 0; i < list.size(); i++) {
            if (year.equalsIgnoreCase(list.get(i)))
                return i;
        }
        return 0;
    }

}
