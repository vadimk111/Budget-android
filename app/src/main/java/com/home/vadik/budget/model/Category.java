package com.home.vadik.budget.model;


import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
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
    public Long amount;
    public String parent;
    public long order = 0;
    public Boolean isBill;
    public List<Expense> expenses;

    public List<Category> subCategories;

    public Category() {
        super();
    }

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

    public long calculatedAmount() {
        long m_amount = 0;

        if (subCategories == null) {
            if (amount != null) {
                return amount;
            }
            return m_amount;
        }

        for (Iterator<Category> i = subCategories.iterator(); i.hasNext();) {
            Category c = i.next();
            if (c.amount != null) {
                m_amount += c.amount;
            }
        }

        return m_amount;
    }

    public long calculatedTotalSpent() {
        long totalSpent = 0;

        if (expenses != null) {
            for (Iterator<Expense> i = expenses.iterator(); i.hasNext();) {
                Expense e = i.next();
                if (e.amount != null) {
                    totalSpent += e.amount;
                }
            }
        }

        if (subCategories == null) {
            return totalSpent;
        }

        for (Iterator<Category> i = subCategories.iterator(); i.hasNext();) {
            totalSpent += i.next().calculatedTotalSpent();
        }

        return totalSpent;
    }

    public Category makeCopy() {
        Category copy = new Category();
        copy.id = id;
        copy.title = title;
        copy.amount = amount;
        copy.order = order;
        copy.parent = parent;
        copy.isBill = isBill;

        if (expenses != null) {
            List<Expense> expensesCopy = new ArrayList<>();
            for (Iterator<Expense> i = expenses.iterator(); i.hasNext();) {
                expensesCopy.add(i.next().makeCopy());
            }
            copy.expenses = expensesCopy;
        }

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
        if (parent != null) {
            result.put(parentKey, parent);
        }
        result.put(orderKey, order);

        if (isBill != null) {
            result.put(isBillKey, isBill);
        }

        Map<String, Map<String, Object>> expensesObj = new HashMap<>();
        if (expenses != null) {
            for (Iterator<Expense> i = expenses.iterator(); i.hasNext();) {
                Expense e = i.next();
                if (e.id != null) {
                    expensesObj.put(e.id, e.toValues());
                }
            }
            if (expensesObj.keySet().size() > 0) {
                result.put(expensesKey, expensesObj);
            }
        }

        return result;
    }

    @Override
    public void delete() {
        super.delete();

        if (subCategories != null) {
            for (Iterator<Category> i = subCategories.iterator(); i.hasNext();) {
                i.next().delete();
            }
        }
    }
}
