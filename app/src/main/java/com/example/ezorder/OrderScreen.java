package com.example.ezorder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderScreen extends AppCompatActivity {
    private static final String TAG = "Dinner Table";

    GridView gridView;
    int status, tNum, userID;
    String tbID, documentID;
    List<Table> tableList;
    OrderAdapter adapter;
    FirebaseFirestore db;
    Map<String, Object> data = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        gridView = findViewById(R.id.order_layout);

        getListTable();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Table table = (Table) parent.getItemAtPosition(position);
                tbID = table.getTableID();
                tNum = table.getNumber();
                status = table.getStatus();

                if (status==0){
                    UUID uuid = UUID.randomUUID();
                    documentID = uuid.toString();
                    data.put("DocumentID",documentID);
                    db.collection("Order").document(documentID)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"Gọi món cho bàn "+tNum,Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.getMessage());
                                }
                            });
                    order(0);   //gọi order mới
                }
                else{
                    //lấy giá trị DocumentID đã có trong bàn
                    db.collection("Table").whereEqualTo("tableID",tbID)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                            documentID = String.valueOf(snapshot.getData().get("DocumentID"));
                                        }
                                    } else {
                                        Log.d(TAG, "onComplete: Load data error " + task.getException());
                                    }
                                }
                            });
                    showPopupMenu(OrderScreen.this, view);
                }
            }
        });
    }

    private void showPopupMenu(Activity act, View view){
        PopupMenu popupMenu = new PopupMenu(act,view);
        popupMenu.getMenuInflater().inflate(R.menu.layout_popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_order:
                        Toast.makeText(getApplicationContext(),"Gọi thêm món cho bàn " + tNum,Toast.LENGTH_SHORT).show();
                        order(1);// gọi thêm trong order cũ
                        break;
                    case R.id.menu_bill:
                        Toast.makeText(getApplicationContext(),"Hiện hóa đơn của bàn " + tNum,Toast.LENGTH_SHORT).show();
                        //show bill
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void order(int orderNumber){
        Intent intent = new Intent(OrderScreen.this, Order_Sub_1.class);
        intent.putExtra("tblID",tbID);
        intent.putExtra("tbNum", tNum);
        intent.putExtra("status", status);
        intent.putExtra("DocumentID", documentID);
        intent.putExtra("orderNumber", orderNumber);
        intent.putExtra("USERID", userID);
        startActivity(intent);
    }
    private void getListTable(){
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

    @Override
    protected void onResume() {
        Toast.makeText(getApplicationContext(),"Đang tải danh sách bàn ăn",Toast.LENGTH_SHORT).show();
        getListTable();
        super.onResume();
    }
}
