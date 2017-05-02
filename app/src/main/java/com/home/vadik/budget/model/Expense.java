package com.home.vadik.budget.model;

import com.google.firebase.database.DataSnapshot;

import java.util.Date;
import java.util.Map;

/**
 * Created by vadik on 25/04/2017.
 */

public class Expense extends ModelBaseObject {

    private static String titleKey = "title";
    private static String amountKey = "amount";
    private static String dateKey = "date";

    public String title;
    public long amount;
    public Date date;

    public Expense(DataSnapshot snapshot) {
        super(snapshot);

        Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
        title = (String) value.get(titleKey);
        amount = (Long) value.get(amountKey);
        date = ModelHelper.stringToDate((String) value.get(dateKey));
    }
}
