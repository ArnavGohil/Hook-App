package com.example.cn;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;

public class SharingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        Bundle bundle = getIntent().getExtras();
        String data = bundle.getString(getString(R.string.selected));

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION)) {
            Snackbar.make(findViewById(R.id.rv), "NFC not supported on device. Try QR based sharing.", Snackbar.LENGTH_SHORT)
                    .setTextColor(Color.parseColor("#F9AA33"))
                    .setBackgroundTint(Color.parseColor("#344955"))
                    .show();
            return;
        }

        TextView tv = findViewById(R.id.tv);
        LottieAnimationView anim = findViewById(R.id.animfinal);
        new Handler().postDelayed(() -> {
            anim.setAnimation(R.raw.done);
            anim.playAnimation();
            anim.loop(false);
            tv.setText("Done !");
        }, 2500);

    }
}