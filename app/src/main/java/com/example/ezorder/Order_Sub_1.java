package com.example.ezorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.ezorder.Adapter.CustomDialog;
import com.example.ezorder.Adapter.Order_Sub_1_Adapter;

import java.util.ArrayList;
import java.util.List;

public class Order_Sub_1 extends AppCompatActivity {
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__sub_1);
        gridView = findViewById(R.id.choose_food_grview);
        List<String> list = new ArrayList<>();
        list.add("Apple");
        list.add("Orange");
        list.add("Banana");
        list.add("Melon");
        list.add("Cherry");
        list.add("Mango");
        Order_Sub_1_Adapter adapter = new Order_Sub_1_Adapter(getApplicationContext(),
                R.drawable.ic_launcher_foreground, list);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                CustomDialog dialog = new CustomDialog();
                dialog.show(transaction, null);
            }
        });
    }
}