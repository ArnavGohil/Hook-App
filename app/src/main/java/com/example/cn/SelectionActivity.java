package com.example.cn;

import android.app.ActivityOptions;
import android.content.Intent;
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

        NeumorphCardView
                phone = findViewById(R.id.flat_card),
                email = findViewById(R.id.flat_card1),
                facebook = findViewById(R.id.flat_card2),
                linkedin = findViewById(R.id.flat_card3),
                instagram = findViewById(R.id.flat_card4),
                telegram = findViewById(R.id.flat_card5),
                twitter = findViewById(R.id.flat_card6),
                whatsapp = findViewById(R.id.flat_card7),
                snapchat = findViewById(R.id.flat_card8),
                address = findViewById(R.id.flat_card9);

        NeumorphFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {

            String str = "";

            if (phone.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_phone) + " ");

            if (email.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_mail) + " ");

            if (facebook.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_facebook) + " ");

            if (linkedin.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_linkedin) + " ");

            if (instagram.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_instagram) + " ");

            if (telegram.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_telegram) + " ");

            if (twitter.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_twitter) + " ");

            if (whatsapp.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_whatsapp) + " ");

            if (snapchat.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_snapchat) + " ");

            if (address.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat(getString(R.string.user_address) + " ");


            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SelectionActivity.this);
            startActivity(new Intent(SelectionActivity.this, SharingActivity.class).putExtra(getString(R.string.selected), str), options.toBundle());
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