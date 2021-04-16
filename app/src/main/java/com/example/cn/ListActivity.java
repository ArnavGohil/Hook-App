package com.example.cn;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SharedPreferences preferences = getSharedPreferences(getString(R.string.file_name), MODE_PRIVATE);
        Set<String> set = preferences.getStringSet(getString(R.string.set_received), new HashSet<>());

        ArrayList<String> nameArray = new ArrayList<>();
        ArrayList<String> dataArray = new ArrayList<>();

        for (String s : set)
        {
            int loc = s.indexOf('|');
            nameArray.add(s.substring(0 , loc));
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
}