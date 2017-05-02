package com.home.vadik.budget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.home.vadik.budget.model.Category;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private ArrayList<Category> categories = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };
    private DatabaseReference myRef;
    private ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listView);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("budgets").child("eWwVm0avyoatMgYoyaoR82SdKwt120173");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (Iterator<DataSnapshot> i = dataSnapshot.getChildren().iterator(); i.hasNext(); ) {
                    categories.add(new Category(i.next()));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        };
        myRef.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myRef.removeEventListener(valueEventListener);
    }
}
