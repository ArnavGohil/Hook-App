package com.example.cn;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Timer;
import java.util.TimerTask;

import soup.neumorphism.NeumorphButton;

public class SharingActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

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
            try {
                BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 512, 512);

                Bitmap bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_4444);
                for (int x = 0; x < 512; x++) {
                    for (int y = 0; y < 512; y++) {
                        bmp.setPixel(x, y, matrix.get(x, y) ? Color.WHITE : getColor(R.color.trans));
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
        } else
            nfcAdapter = NfcAdapter.getDefaultAdapter(this);

/*
        new Handler().postDelayed(() -> {
            anim.setAnimation(R.raw.done);
            anim.playAnimation();
            anim.loop(false);
            tv.setText("Done !");
        }, 2500);
*/

        // TODO Turn off NFC receiving

        Context context = this;
        Timer t = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(context, CardService.class);
                intent.putExtra("ndefMessage", data);
                startService(intent);
            }

        };

//        t.scheduleAtFixedRate(task, 0, 10000);

        Intent intent = new Intent(context, CardService.class);
        intent.putExtra("ndefMessage", data);
        startService(intent);

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    protected void onPause() {
        super.onPause();

        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        resolveIntent(intent);
    }


    private void resolveIntent(Intent intent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.e("HostApduService", print(tag.getTechList()));
        }
    }

    private String print(String[] techList) {
        StringBuilder str = new StringBuilder();
        for (String s : techList)
            str.append(s).append("\n");
        return str.toString();
    }
}