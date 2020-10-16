package com.example.ezorder;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ezorder.Adapter.ChefAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChefScreen extends AppCompatActivity {
    ListView listView;
    List<String> food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_screen);
        food = new ArrayList<>();
        food.add("Keo");
        food.add("Banh");
        food.add("Bia");
        food.add("Cha muc");
        listView = findViewById(R.id.chef_listview);
        ChefAdapter chefAdapter = new ChefAdapter(getApplicationContext(), R.drawable.ic_dinner_table, food
        );
        listView.setAdapter(chefAdapter);
    }
}