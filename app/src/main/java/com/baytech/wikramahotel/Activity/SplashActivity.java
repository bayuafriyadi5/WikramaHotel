package com.baytech.wikramahotel.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.baytech.wikramahotel.R;

public class SplashActivity extends AppCompatActivity {

    Animation app_splash;
    Animation subtitle;
    TextView app_subtitle;
    ImageView app_logo;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        app_logo = findViewById(R.id.app_logo);

        app_subtitle = findViewById(R.id.app_subtitle);

//        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
//
//        subtitle = AnimationUtils.loadAnimation(this,R.anim.subtitle);
//
//        app_logo.startAnimation(app_splash);
//
//        app_subtitle.startAnimation(subtitle);

        getUsernameLocal();
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY,MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
        if(username_key_new.isEmpty()){
            Handler handler= new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            },7000);
        }else{
            Handler handler= new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this,SliderActivity.class);
                startActivity(intent);
                finish();
            },7000);
        }
    }
}
