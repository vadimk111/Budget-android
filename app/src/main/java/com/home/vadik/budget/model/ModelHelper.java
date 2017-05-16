package com.home.vadik.budget.model;

import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by vadik on 25/04/2017.
 */

public class ModelHelper {

    private static String uidPreferencesKey = Consts.sharedPreferencesFileName + ".uid";
    private static String automaticAuthenticationCompletedPreferencesKey = Consts.sharedPreferencesFileName + ".automaticAuthenticationCompleted";
    private static String dateFormat = "dd-MM-yyyy";
    private static String[] weekDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

    public static String budgetIdForDate(Date date, SharedPreferences preferences) {
        String uid = preferences.getString(uidPreferencesKey, null);
        if (uid == null && preferences.getBoolean(automaticAuthenticationCompletedPreferencesKey, false)) {
            uid = preferences.getString(Consts.emailPreferencesKey, null);
        }

        if (uid != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);

            return uid + Integer.toString(year) + Integer.toString(month);
        }
        return null;
    }

    public static Date stringToDate(String dateStr) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String dateToString(Date date) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    public Date nextMonthFromDate(Date date) {
        return changeDate(date, true);
    }

    public Date prevMonthToDate(Date date) {
        return changeDate(date, false);
    }

    private Date changeDate(Date date, boolean forward) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        if (forward) {
            if (month == 12) {
                year += 1;
                month = 1;
            } else {
                month += 1;
            }
        } else {
            if (month == 1) {
                year -= 1;
                month = 12;
            } else {
                month -= 1;
            }
        }

        calendar.set(year, month, 1);

        return calendar.getTime();
    }

    public String weekDayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDays[weekday - 1];
    }

    public int dayOfMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}
