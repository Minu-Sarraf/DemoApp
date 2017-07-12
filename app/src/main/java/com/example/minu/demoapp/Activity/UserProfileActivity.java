package com.example.minu.demoapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minu.demoapp.R;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {
    TextView txName;
    CircularImageView imPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        txName = (TextView) findViewById(R.id.userName);
        imPic = (CircularImageView) findViewById(R.id.userImg);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        uploadImg();

    }

    private void uploadImg() {
        //saved data during registration
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        txName.setText(sp.getString("name","Unknown User"));
        Picasso.with(this).load(Uri.parse(sp.getString("uri", ""))).resize(150, 150).placeholder(R.drawable.leapfroglogo).into(imPic);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
    }

    public void logout() {
        FirebaseAuth.getInstance()
                .signOut();
              /*  .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(UserProfileActivity.this,LoginActivity.class));
                        finish();
                    }
                });*/
    }
}
