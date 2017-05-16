package com.home.vadik.budget.authentication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.home.vadik.budget.model.Consts;

/**
 * Created by vadik on 10/05/2017.
 */

public class Authentication {

    private static String anonymous = "-anonymous-";

    public void automaticSignIn(final Context context) {
        final SharedPreferences preferences =  context.getSharedPreferences(Consts.sharedPreferencesFileName, Context.MODE_PRIVATE);
        String email = preferences.getString(Consts.emailPreferencesKey, null);
        String password = preferences.getString(Consts.passwordPreferencesKey, null);

        if (email != null && password != null) {
            if (email.contains(anonymous)) {
                //notifyStateChanged();
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    manualSignIn(preferences);
                                    Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            manualSignIn(preferences);
        }
    }

    private void manualSignIn(SharedPreferences preferences) {

    }

}
