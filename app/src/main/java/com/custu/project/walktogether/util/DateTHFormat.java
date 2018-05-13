package com.custu.project.walktogether.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private String months[] = {
            "ม.ค", "ก.พ", "มี.ค", "เม.ย",
            "พ.ค", "มิ.ย", "ก.ค", "ส.ค",
            "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};

    public boolean isDateValid(String dateToValidate) {
        if (dateToValidate == null) {
            return false;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        formatter.setLenient(false);
        try {
            Date date = formatter.parse(dateToValidate);
            System.out.println(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public String normalDateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

    public String getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = 0;
        int year = 0;
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        SimpleDateFormat formatter = new SimpleDateFormat("MM yy", new Locale("th", "TH"));
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return months[month] + " " + String.valueOf(year + 543).substring(2, 4);
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

    public void isTestEvaluation(Date lastDate) {
        Calendar lastDateCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+7"));
        lastDateCalendar.set(Calendar.ZONE_OFFSET, TimeZone.getTimeZone("GMT+7").getRawOffset());
        lastDateCalendar.setTime(lastDate);
        lastDateCalendar.add(Calendar.DATE, 3);
    }

    public String birthDayToAge(String input) {
        Date birthDay = stringToDate(input);
        Date dateNow = new Date();
        Long dateTime = dateNow.getTime() - birthDay.getTime();
        Long year = Math.abs(dateTime / 1000 / 60 / 60 / 24);
        return String.valueOf(year / 365);
    }

    private Date stringToDate(String dateString) {
        String newDate = dateString.split(" ")[0] + " " + dateString.split(" ")[1] + " " + (Integer.parseInt(dateString.split(" ")[2]) - 543);
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
        Log.d("stringToDate: ", "stringToDate: " + newDate);
        try {
            return formatter.parse(newDate);
        } catch (ParseException e) {
            return new Date();
        }
    }
}

