package com.example.cn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphFloatingActionButton;

public class ReceivingActivity extends AppCompatActivity {

    ImageView photo;
    NeumorphCardView phone, email, facebook, linkedin, instagram, telegram, twitter, whatsapp, snapchat, address;
    String naam, temp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String data = appLinkIntent.getDataString();
        boolean flag = true ;
        if (data == null)
        {
            flag = false;
            Bundle bundle = getIntent().getExtras();
            data = bundle.getString(getString(R.string.selected));
        }
        data = data.replaceAll("%20", " ");
        temp = data ;
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
        NeumorphFloatingActionButton fab = findViewById(R.id.fab) , fabSha = findViewById(R.id.fabSha);
        fab.setVisibility(View.INVISIBLE);
        fabSha.setVisibility(View.INVISIBLE);
        photo = findViewById(R.id.recPhoto);
        HashSet<Integer> set = new HashSet<>();

        try {
            JSONArray jsonArray = new JSONArray(data);

            naam = jsonArray.getString(0);
            setPhoto(jsonArray.getString(1));

            for (int i = 2; i < jsonArray.length(); i++) {
                String x = jsonArray.getString(i);
                int loc = x.indexOf('|');
                int j = Integer.parseInt(x.substring(0, loc));
                NeumorphCardView card = returnView(j);
                card.setStrokeColor(getColorStateList(R.color.colorAccent));
                card.setOnClickListener(returnListener(j, x.substring(loc + 1)));
                set.add(j);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 1; i <= 10; i++) {
            if (!set.contains(i)) {
                returnView(i).setOnClickListener(view -> {
                });
            }
        }

        name.setText(naam);

        if (flag) {
            //TODO Check Bug
            SharedPreferences preferences = getSharedPreferences(getString(R.string.file_name), MODE_PRIVATE);
            Set<String> setRec = preferences.getStringSet(getString(R.string.set_received), new HashSet<>());
            Log.e("TAG", new ArrayList<>(set).toString());
            setRec.add(naam + "|" + temp);
            Log.e("TAG", new ArrayList<>(set).toString());
            SharedPreferences.Editor edit = preferences.edit();
            edit.putStringSet(getString(R.string.set_received), setRec);
            edit.apply();
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

    private View.OnClickListener returnListener(int i, String input) {
        switch (i) {
            case 1:
                return view -> {
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, naam);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, input);
                    startActivity(intent);
                };
            case 2:
                return view -> {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, input);
                    startActivity(intent);
                };
            case 3:
                return view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.facebook.com/" + input));
                    startActivity(intent);
                };
            case 4:
                return view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://linkedin.com/in/" + input));
                    startActivity(intent);
                };
            case 5:
                return view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.instagram.com/" + input));
                    startActivity(intent);
                };
            case 6:
                return view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://t.me/" + input));
                    startActivity(intent);
                };
            case 7:
                return view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.twitter.com/" + input));
                    startActivity(intent);
                };
            case 8:
                return view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://wa.me/" + input));
                    startActivity(intent);
                };
            case 9:
                return view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.snapchat.com/add/" + input));
                    startActivity(intent);
                };
            case 10:
                return view -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://www.google.com/maps/place/" + input.replace(" ", "%20").replace("+", "%2B")));
                    startActivity(intent);
                };
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

}