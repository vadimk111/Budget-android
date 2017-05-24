package com.home.vadik.budget.model;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by vadik on 25/04/2017.
 */

public class Expense extends ModelBaseObject {

    private static String titleKey = "title";
    private static String amountKey = "amount";
    private static String dateKey = "date";

    public String title;
    public Double amount;
    public Date date;

    public Expense() {
        super();
    }

    public Expense(DataSnapshot snapshot) {
        super(snapshot);

        Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
        title = (String) value.get(titleKey);
        Object amountValue = value.get(amountKey);
        if (Double.class.isInstance(amountValue)) {
            amount = (Double) amountValue;
        } else if (Long.class.isInstance(amountValue)) {
            amount = ((Long) amountValue).doubleValue();
        }
        date = ModelHelper.stringToDate((String) value.get(dateKey));
    }

    public Expense makeCopy() {
        Expense copy = new Expense();
        copy.id = id;
        copy.title = title;
        copy.amount = amount;
        copy.date = date;

        return copy;
    }

    @Override
    public Map<String, Object> toValues() {
        Map<String, Object> result = new HashMap<>();

        if (title != null) {
            result.put(titleKey, title);
        }
        if (amount != null) {
            result.put(amountKey, amount);
        }
        if (date != null) {
            result.put(dateKey, ModelHelper.dateToString(date));
        }

        return result;
    }
}
