package com.home.vadik.budget.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vadik on 25/04/2017.
 */

public class ModelHelper {

    private static String dateFormat = "dd-MM-yyyy";

    public static Date stringToDate(String dateStr) {
        DateFormat format = new SimpleDateFormat(dateFormat);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
