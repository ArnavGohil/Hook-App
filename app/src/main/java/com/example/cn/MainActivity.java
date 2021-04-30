package com.example.cn;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import soup.neumorphism.NeumorphButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NeumorphButton login = findViewById(R.id.login);
        ImageView photo = findViewById(R.id.userPhoto);
        TextView name = findViewById(R.id.userName);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
        startNFC();

        SharedPreferences preferences = getSharedPreferences(getString(R.string.file_name), MODE_PRIVATE);

        boolean first = preferences.contains(getString(R.string.user_name));
        if (!first)
            login.setText("REGISTER");
        else {

            String user_name = preferences.getString(getString(R.string.user_name), "User");
            int sp = user_name.indexOf(' ');
            name.setText("Hello, " + user_name.substring(0, sp == -1 ? user_name.length() : sp));

            String photo_encoded = preferences.getString(getString(R.string.user_photo), "");
            if (photo_encoded.length() != 0) {
                byte[] decodedByte = Base64.decode(photo_encoded, 0);
                Bitmap bmp = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
                photo.setImageBitmap(bmp);
            }

        }


        login.setOnClickListener(view -> {
            if (!first)
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class), options.toBundle());
            else
                startActivity(new Intent(MainActivity.this, SelectionActivity.class), options.toBundle());
        });

        //TODO - Remove when done !
        login.setOnLongClickListener(view -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Toast.makeText(this, "Done !", Toast.LENGTH_SHORT).show();
            return false;
        });

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