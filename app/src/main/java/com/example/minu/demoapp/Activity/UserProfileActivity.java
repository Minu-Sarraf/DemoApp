package com.example.minu.demoapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.minu.demoapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
    }

    public void logout() {
        FirebaseAuth.getInstance()
                .signOut();
               /* .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(UserProfileActivity.this,LoginActivity.class));
                        finish();
                    }
                });*/
    }
}
