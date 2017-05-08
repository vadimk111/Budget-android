package com.home.vadik.budget.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vadik on 25/04/2017.
 */

public class ModelBaseObject {
    private DatabaseReference ref;
    public String id;

    public ModelBaseObject() {

    }

    public ModelBaseObject(DataSnapshot snapshot) {
        ref = snapshot.getRef();
        id = snapshot.getKey();
    }

    public Map<String, Object> toValues() {
        return new HashMap<>();
    }

    public void delete() {
        ref.removeValue();
    }

    public String insertInto(DatabaseReference parent) {
        DatabaseReference child = parent.push();
        child.setValue(toValues());
        return child.getKey();
    }

    public void update() {
        ref.setValue(toValues());
    }

    public void removeChild(String path) {
        ref.child(path).removeValue();
    }

    public DatabaseReference getDatabaseReference() {
        return ref;
    }

    public void setDatabaseReference(DatabaseReference reference) {
        ref = reference;
    }
}
