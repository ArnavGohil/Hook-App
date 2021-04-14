package com.example.cn;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SharingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

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