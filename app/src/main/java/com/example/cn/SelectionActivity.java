package com.example.cn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import soup.neumorphism.NeumorphFloatingActionButton;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        NeumorphFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SelectionActivity.this);
            startActivity(new Intent(SelectionActivity.this, SharingActivity.class), options.toBundle());
        });
    }
}