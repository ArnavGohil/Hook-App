package com.example.cn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphFloatingActionButton;

public class ReceivingActivity extends AppCompatActivity {

    ImageView photo;
    NeumorphCardView phone, email, facebook, linkedin, instagram, telegram, twitter, whatsapp, snapchat, address;
    HashMap<Integer, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String data = appLinkIntent.getDataString();
        data = data.substring(data.lastIndexOf("/") + 1);

        phone = findViewById(R.id.flat_card);
        email = findViewById(R.id.flat_card1);
        facebook = findViewById(R.id.flat_card2);
        linkedin = findViewById(R.id.flat_card3);
        instagram = findViewById(R.id.flat_card4);
        telegram = findViewById(R.id.flat_card5);
        twitter = findViewById(R.id.flat_card6);
        whatsapp = findViewById(R.id.flat_card7);
        snapchat = findViewById(R.id.flat_card8);
        address = findViewById(R.id.flat_card9);
        TextView name = findViewById(R.id.recName),
                desc = findViewById(R.id.recDes);
        desc.setText("Shared these handles with you - ");
        NeumorphFloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        photo = findViewById(R.id.recPhoto);

        map = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray(data);

            name.setText(jsonArray.getString(0));
            setPhoto(jsonArray.getString(1));

            for (int i = 2; i < jsonArray.length(); i++) {
                String x = jsonArray.getString(i);
                int loc = x.indexOf('|');
                map.put(Integer.parseInt(x.substring(0, loc)), x.substring(loc + 1));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (int i = 0; i <= 10; i++) {
            if (map.containsKey(i)) {
                NeumorphCardView card = returnView(i);
                card.setStrokeColor(getColorStateList(R.color.colorAccent));
            }

        }

    }

    private NeumorphCardView returnView(int i) {
        switch (i) {
            case 1:
                return phone;
            case 2:
                return email;
            case 3:
                return facebook;
            case 4:
                return linkedin;
            case 5:
                return instagram;
            case 6:
                return telegram;
            case 7:
                return twitter;
            case 8:
                return whatsapp;
            case 9:
                return snapchat;
            case 10:
                return address;
        }

        return null;
    }

    private void setPhoto(String enc) {
        if (enc.length() == 0)
            return;

        byte[] decodedByte = Base64.decode(enc, 0);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        photo.setImageBitmap(bmp);
    }

    public void select(View view) {
        NeumorphCardView card = (NeumorphCardView) view;
        // TODO Listener
    }
}