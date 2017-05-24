package com.home.vadik.budget.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.home.vadik.budget.R;
import com.home.vadik.budget.model.Category;
import com.home.vadik.budget.model.Consts;
import com.home.vadik.budget.model.ModelHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    private static String anonymous = "-anonymous-";

    private ListView mListView;

    private ArrayList<Category> categories = new ArrayList<>();

    private DatabaseReference budgetRef;
    private ValueEventListener valueEventListener;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private SharedPreferences preferences;
    private FirebaseUser user;

    public Date date = new Date();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(Consts.sharedPreferencesFileName, Context.MODE_PRIVATE);

        mListView = (ListView) findViewById(R.id.listView);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (user == null) {
                    user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        preferences.edit().putString(Consts.uidPreferencesKey, user.getUid()).apply();
                        loadBudget();
                    }
                } else {
                    FirebaseUser _user = firebaseAuth.getCurrentUser();
                    if (_user == null) {
                        user = null;
                        preferences.edit().remove(Consts.uidPreferencesKey).apply();
                        loadBudget();
                    } else if (_user.getUid() != user.getUid()) {
                        user = _user;
                        preferences.edit().putString(Consts.uidPreferencesKey, user.getUid()).apply();
                        loadBudget();
                    }
                }
            }
        };
        automaticSignIn();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (budgetRef != null) {
            budgetRef.removeEventListener(valueEventListener);
        }
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void automaticSignIn() {
        String email = preferences.getString(Consts.emailPreferencesKey, null);
        String password = preferences.getString(Consts.passwordPreferencesKey, null);

        if (email != null && password != null) {
            if (email.contains(anonymous)) {
                //notifyStateChanged();
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    manualSignIn();
                                    Toast.makeText(MainActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            manualSignIn();
        }
    }

    private void manualSignIn() {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void loadBudget() {
        String budgetId = ModelHelper.budgetIdForDate(date, preferences);
        if (budgetId != null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            budgetRef = database.getReference("budgets").child(budgetId);
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
            budgetRef.addValueEventListener(valueEventListener);
        }
    }
}
