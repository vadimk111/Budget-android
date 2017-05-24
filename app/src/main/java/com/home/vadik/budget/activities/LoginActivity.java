package com.home.vadik.budget.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.home.vadik.budget.R;
import com.home.vadik.budget.model.Consts;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;

    private TextInputLayout emailField;
    private TextInputLayout passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(Consts.sharedPreferencesFileName, Context.MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();
        emailField = (TextInputLayout) findViewById(R.id.email_input);
        passwordField = (TextInputLayout) findViewById(R.id.password_input);
    }

    public void signInClicked(View view) {
        final String email = emailField.getEditText().getText().toString();
        final String password = passwordField.getEditText().getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(Consts.emailPreferencesKey, email);
                            editor.putString(Consts.passwordPreferencesKey, password);
                            editor.apply();
                            finish();
                        } else {
                            Toast toast = Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                        }
                    }
                });
    }

    public void createAccountClicked(View view) {
        final String email = emailField.getEditText().getText().toString();
        final String password = passwordField.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(Consts.emailPreferencesKey, email);
                            editor.putString(Consts.passwordPreferencesKey, password);
                            editor.apply();
                            finish();
                        } else {
                            Toast toast = Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                        }
                    }
                });
    }

    public void resetPasswordClicked(View view) {
        Toast.makeText(this, "reset password", Toast.LENGTH_SHORT).show();
    }
}
