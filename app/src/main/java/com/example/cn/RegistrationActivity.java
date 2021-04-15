package com.example.cn;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegistrationActivity extends AppCompatActivity {

    int RESULT_LOAD_IMAGE = 9;
    ImageView photo;
    String imageEncoded = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        SharedPreferences preferences = getSharedPreferences(getString(R.string.file_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        EditText name = findViewById(R.id.name),
                phone = findViewById(R.id.phone),
                email = findViewById(R.id.email),
                facebook = findViewById(R.id.facebook),
                linkedin = findViewById(R.id.linkedin),
                instagram = findViewById(R.id.insta),
                telegram = findViewById(R.id.telegram),
                twitter = findViewById(R.id.twitter),
                whatsapp = findViewById(R.id.whatsapp),
                snapchat = findViewById(R.id.snapchat),
                address = findViewById(R.id.map);

        photo = findViewById(R.id.regPhoto);
        photo.setOnClickListener(view -> {
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        });


        Button save = findViewById(R.id.done);
        save.setOnClickListener(view -> {
            String naam = name.getText().toString();
            if (naam.length() == 0) {
                Snackbar.make(findViewById(R.id.scrollView), "Name can not be empty  !", Snackbar.LENGTH_SHORT)
                        .setTextColor(Color.parseColor("#F9AA33"))
                        .setBackgroundTint(Color.parseColor("#344955"))
                        .show();
                return;
            }

            String phn = phone.getText().toString(),
                    em = email.getText().toString(),
                    fb = facebook.getText().toString(),
                    lin = linkedin.getText().toString(),
                    insta = instagram.getText().toString(),
                    tele = telegram.getText().toString(),
                    twi = twitter.getText().toString(),
                    wap = whatsapp.getText().toString(),
                    sc = snapchat.getText().toString(),
                    add = address.getText().toString();

            editor.putString(getString(R.string.user_name), naam);
            editor.putString(getString(R.string.user_phone), phn);
            editor.putString(getString(R.string.user_mail), em);
            editor.putString(getString(R.string.user_facebook), fb);
            editor.putString(getString(R.string.user_linkedin), lin);
            editor.putString(getString(R.string.user_instagram), insta);
            editor.putString(getString(R.string.user_telegram), tele);
            editor.putString(getString(R.string.user_twitter), twi);
            editor.putString(getString(R.string.user_whatsapp), wap);
            editor.putString(getString(R.string.user_snapchat), sc);
            editor.putString(getString(R.string.user_address), add);
            editor.putString(getString(R.string.user_photo), imageEncoded);
            editor.apply();

            Toast.makeText(this, "Saved !", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(RegistrationActivity.this, SelectionActivity.class),
                    ActivityOptions.makeSceneTransitionAnimation(RegistrationActivity.this).toBundle());
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                photo.setImageBitmap(selectedImage);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}