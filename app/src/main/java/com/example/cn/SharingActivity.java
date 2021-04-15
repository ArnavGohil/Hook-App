package com.example.cn;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import soup.neumorphism.NeumorphButton;

public class SharingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString(getString(R.string.selected));

        NeumorphButton button = findViewById(R.id.QR);
        TextView tv = findViewById(R.id.tv);
        LottieAnimationView anim = findViewById(R.id.animfinal);
        ImageView QRImage = findViewById(R.id.QRImage);

        button.setOnClickListener(view -> {
            tv.setVisibility(View.INVISIBLE);
            anim.setVisibility(View.INVISIBLE);
            button.setClickable(false);
            //TODO Generate QR and Print.
            try {
                BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 512, 512);

                Bitmap bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_4444);
                for (int x = 0; x < 512; x++) {
                    for (int y = 0; y < 512; y++) {
                        bmp.setPixel(x, y, matrix.get(x, y) ? getColor(R.color.colorAccent) : getColor(R.color.trans));
                    }
                }
                QRImage.setImageBitmap(bmp);
                button.setText("YOUR QR CODE");
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)) {
            Snackbar.make(findViewById(R.id.rv), "NFC not supported on device. Try QR based sharing.", Snackbar.LENGTH_SHORT)
                    .setAction("QR CODE", view1 -> {
                        tv.setText("Not Sharing...");
                        button.performClick();
                    })
                    .setActionTextColor(Color.parseColor("#F9AA33"))
                    .setTextColor(Color.parseColor("#FFFFFF"))
                    .setBackgroundTint(Color.parseColor("#344955"))
                    .show();
            return;
        }

        new Handler().postDelayed(() -> {
            anim.setAnimation(R.raw.done);
            anim.playAnimation();
            anim.loop(false);
            tv.setText("Done !");
        }, 2500);

    }
}