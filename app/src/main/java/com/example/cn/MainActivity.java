package com.example.cn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import soup.neumorphism.NeumorphButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NeumorphButton login = findViewById(R.id.login) ;
        ImageView photo = findViewById(R.id.userPhoto) ;
        TextView name = findViewById(R.id.userName);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);

        photo.setOnClickListener(view -> {
            // TODO
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class), options.toBundle());
        });

        login.setOnClickListener(view -> {
            // TODO
            startActivity(new Intent(MainActivity.this, SelectionActivity.class), options.toBundle());
        });

    }
}