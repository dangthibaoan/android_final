package com.example.ezorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ezorder.Adapter.OrderAdapter;
import com.example.ezorder.SubActivity.Order_Sub_1;

import java.util.ArrayList;
import java.util.List;

public class OrderScreen extends AppCompatActivity {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Orange");
        list.add("Banana");
        list.add("Melon");
        list.add("Cherry");
        list.add("Mango");

        gridView = findViewById(R.id.order_layout);
        OrderAdapter orderAdapter = new OrderAdapter(this, R.drawable.ic_dinner_table, list);
        gridView.setAdapter(orderAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(OrderScreen.this, Order_Sub_1.class));
            }
        });
    }
}