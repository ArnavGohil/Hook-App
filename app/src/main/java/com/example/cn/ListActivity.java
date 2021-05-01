package com.example.cn;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SharedPreferences preferences = getSharedPreferences(getString(R.string.file_name), MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) preferences.getStringSet(getString(R.string.set_received), new HashSet<>());

        ArrayList<String> nameArray = new ArrayList<>();
        ArrayList<String> dataArray = new ArrayList<>();

        for (String s : set) {
            int loc = s.indexOf('|');
            nameArray.add(s.substring(0, loc));
            dataArray.add(s.substring(loc + 1));
        }

        ListView lv = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, nameArray);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            String data = dataArray.get(position);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ListActivity.this);
            startActivity(new Intent(ListActivity.this, ReceivingActivity.class).putExtra(getString(R.string.selected), data), options.toBundle());
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SelectionActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}