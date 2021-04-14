package com.example.cn;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this);
            startActivity(new Intent(SplashScreen.this, MainActivity.class), options.toBundle());
        }, 1750);
    }
}