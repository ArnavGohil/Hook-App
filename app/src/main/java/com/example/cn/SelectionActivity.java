package com.example.cn;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import soup.neumorphism.NeumorphCardView;
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

    public void select(View view) {
        NeumorphCardView card = (NeumorphCardView) view;
        if (card.getStrokeColor() == getColorStateList(R.color.selection))
            card.setStrokeColor(getColorStateList(R.color.trans));
        else
            card.setStrokeColor(getColorStateList(R.color.selection));
    }
}