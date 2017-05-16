package com.home.vadik.budget.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.home.vadik.budget.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signInClicked(View view) {
        Toast.makeText(this, "sign in", Toast.LENGTH_SHORT).show();
    }

    public void createAccountClicked(View view) {
        Toast.makeText(this, "create account", Toast.LENGTH_SHORT).show();
    }

    public void resetPasswordClicked(View view) {
        Toast.makeText(this, "reset password", Toast.LENGTH_SHORT).show();
    }
}
