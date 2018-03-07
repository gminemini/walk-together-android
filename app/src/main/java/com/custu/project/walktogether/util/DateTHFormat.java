package com.custu.project.walktogether.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by pannawatnokket on 1/2/2018 AD.
 */

public class DateTHFormat {

    private static DateTHFormat instance;

    public static DateTHFormat getInstance() {
        if (instance == null) {
            instance = new DateTHFormat();
        }
        return instance;
    }

    public String normalDateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public String getMonth(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", new Locale("th", "TH"));
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public String normalDateFormatPlus(Date date) {
        Date newDate = new Date();
        newDate.setDate(date.getDate());
        newDate.setMonth(date.getMonth());
        newDate.setYear(date.getYear() + 543);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(newDate);
    }

    public String slashDateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy", new Locale("th", "TH"));
        return formatter.format(date);
    }

    public String timeFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", new Locale("th", "TH"));
        return formatter.format(date);
    }

    public String birthDayToAge(String input) {
        Date birthDay = stringToDate(input);
        Date dateNow = new Date();
        Long dateTime = dateNow.getTime() - birthDay.getTime();
        Long year = Math.abs(dateTime / 1000 / 60 / 60 / 24);
        return String.valueOf(year / 365);
    }

    private Date stringToDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public Boolean validDate(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            String day = String.valueOf(date.getDate());
            String month = String.valueOf(date.getMonth());
            String year = String.valueOf(date.getYear());
            String dateString = day+"-"+month+"-"+year;
            Date newDate = formatter.parse(dateString);
            Log.d("date", "validDate: "+newDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

