package com.home.vadik.budget.model;


import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by vadik on 01/05/2017.
 */

public class Category extends ModelBaseObject {
    private static String titleKey = "title";
    private static String amountKey = "amount";
    private static String parentKey = "parent";
    private static String orderKey = "order";
    private static String isBillKey = "isBill";
    private static String expensesKey = "expenses";

    public String title;
    public long amount;
    public String parent;
    public long order;
    public Boolean isBill;
    public List<Expense> expenses;

    public Category(DataSnapshot snapshot) {
        super(snapshot);

        Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
        title = (String) value.get(titleKey);
        amount = (Long) value.get(amountKey);
        parent = (String) value.get(parentKey);
        order = (Long) value.get(orderKey);
        isBill = (Boolean) value.get(isBillKey);

        for (Iterator<DataSnapshot> i = snapshot.getChildren().iterator(); i.hasNext();) {
            DataSnapshot childSnapshot = i.next();
            if (childSnapshot.getKey().equals(expensesKey)) {
                expenses = new ArrayList<>();
                for (Iterator<DataSnapshot> j = childSnapshot.getChildren().iterator(); j.hasNext();) {
                    expenses.add(new Expense(j.next()));
                }
            }
        }
    }

}
