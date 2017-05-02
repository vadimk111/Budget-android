package com.home.vadik.budget.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by vadik on 25/04/2017.
 */

public class ModelBaseObject {
    private DatabaseReference ref;
    public String id;

    public ModelBaseObject(DataSnapshot snapshot) {
        ref = snapshot.getRef();
        id = snapshot.getKey();
    }
}
