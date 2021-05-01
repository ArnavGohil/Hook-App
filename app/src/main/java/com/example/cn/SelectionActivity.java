package com.example.cn;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphFloatingActionButton;

public class SelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        startNFC();

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

        NeumorphFloatingActionButton fab = findViewById(R.id.fab), fabSha = findViewById(R.id.fabSha);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SelectionActivity.this);

        fabSha.setOnClickListener(view -> startActivity(new Intent(SelectionActivity.this, ListActivity.class), options.toBundle()));

        fab.setOnClickListener(view -> {

            SharedPreferences preferences = getSharedPreferences(getString(R.string.file_name), MODE_PRIVATE);

            String str = "cn://[";
            str = str.concat("\"" + preferences.getString(getString(R.string.user_name), "") + "\",");
            str = str.concat("\"" /*TODO + preferences.getString(getString(R.string.user_photo), "")*/ + "\",");

            if (phone.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"1|" + preferences.getString(getString(R.string.user_phone), "") + "\",");

            if (email.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"2|" + preferences.getString(getString(R.string.user_mail), "") + "\",");

            if (facebook.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"3|" + preferences.getString(getString(R.string.user_facebook), "") + "\",");

            if (linkedin.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"4|" + preferences.getString(getString(R.string.user_linkedin), "") + "\",");

            if (instagram.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"5|" + preferences.getString(getString(R.string.user_instagram), "") + "\",");

            if (telegram.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"6|" + preferences.getString(getString(R.string.user_telegram), "") + "\",");

            if (twitter.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"7|" + preferences.getString(getString(R.string.user_twitter), "") + "\",");

            if (whatsapp.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"8|" + preferences.getString(getString(R.string.user_whatsapp), "") + "\",");

            if (snapchat.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"9|" + preferences.getString(getString(R.string.user_snapchat), "") + "\",");

            if (address.getStrokeColor() == getColorStateList(R.color.selection))
                str = str.concat("\"10|" + preferences.getString(getString(R.string.user_address), "") + "\",");

            str = str.substring(0, str.length() - 1).concat("]").replaceAll(" ", "%20");
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

    private void startNFC() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            Log.e("TAG", "Helloe Wordle");
            NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
            adapter.enableReaderMode(this,
                    tag -> {
                        try {
                            Ndef ndef = Ndef.get(tag);
                            ndef.connect();
                            NdefMessage mes = ndef.getNdefMessage();
                            byte[] payload = mes.toByteArray();
                            String res = new String(payload);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(res.substring(res.indexOf("cn://"))));
                            startActivity(intent);
                        } catch (FormatException | IOException e) {
                            e.printStackTrace();
                        }
                    },
                    NfcAdapter.FLAG_READER_NFC_A |
                            NfcAdapter.FLAG_READER_NFC_B |
                            NfcAdapter.FLAG_READER_NFC_F |
                            NfcAdapter.FLAG_READER_NFC_V |
                            NfcAdapter.FLAG_READER_NFC_BARCODE,
                    null);
        }
    }

}