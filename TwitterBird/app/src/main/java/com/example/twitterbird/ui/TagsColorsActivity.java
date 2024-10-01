// TagsColorsActivity.java
package com.example.twitterbird.ui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;


import com.example.twitterbird.R;

import java.util.ArrayList;
import java.util.List;

public class TagsColorsActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_colors);

        listView = findViewById(R.id.listView);
        data = new ArrayList<>();  // Populate with data

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
    }
}