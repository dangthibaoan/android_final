package com.example.ezorder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ezorder.Adapter.OrderAdapter;
import com.example.ezorder.Adapter.TableFragmentAdapter;
import com.example.ezorder.Model.Role;
import com.example.ezorder.Model.Table;
import com.example.ezorder.SubActivity.Order_Sub_1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderScreen extends AppCompatActivity {
    private static final String TAG = "Dinner Table";

    GridView gridView;
    int status, tNum, orderID=0;
    String tbID;
    List<Table> tableList;
    OrderAdapter adapter;
    FirebaseFirestore db;

    Thread getListTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        gridView = findViewById(R.id.order_layout);

        getListTable = new Thread() {
            @Override
            public void run() {
                db = FirebaseFirestore.getInstance();
                db.collection("Table")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                tableList = new ArrayList<>();
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                        Table table = snapshot.toObject(Table.class);
                                        tableList.add(table);
                                    }
                                    adapter = new OrderAdapter(getApplicationContext(), R.drawable.ic_dinner_table, tableList);
                                    gridView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "onComplete: Load data error " + task.getException());
                                }
                            }
                        });
            }
        };
        getListTable.start();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Table table = (Table) parent.getItemAtPosition(position);
                tbID = table.getTableID();
                tNum = table.getNumber();
                status = table.getStatus();
                orderID++;
                if (status==0){
                    Toast.makeText(getApplicationContext(),"Gọi món cho bàn " + tNum,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(OrderScreen.this, Order_Sub_1.class);
                    intent.putExtra("tblID",tbID);
                    intent.putExtra("tbNum", tNum);
                    intent.putExtra("status", status);
                    intent.putExtra("orderID", orderID);
                    //startActivityForResult(intent,111);
                    startActivity(intent);
                }
                else{ //chưa có class của bill

                }
            }
        });
    }

}