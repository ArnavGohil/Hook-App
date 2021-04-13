package com.example.cn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextInputLayout phone = findViewById(R.id.phone);
//        Toast.makeText(this, phone.getEndIconDrawable().toString(), Toast.LENGTH_SHORT).show();

    }
}