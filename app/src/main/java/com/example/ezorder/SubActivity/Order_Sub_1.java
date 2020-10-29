package com.example.ezorder.SubActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ezorder.Adapter.FoodFragmentAdapter;
import com.example.ezorder.Adapter.OrderAdapter;
import com.example.ezorder.Adapter.Order_Sub_1_Adapter;
import com.example.ezorder.Model.Food;
import com.example.ezorder.Model.Order;
import com.example.ezorder.Model.Role;
import com.example.ezorder.Model.Table;
import com.example.ezorder.OrderScreen;
import com.example.ezorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Order_Sub_1 extends AppCompatActivity {
    private static final String TAG = "Order Food";

    Map<String, Object> data;

    String documentID;
    GridView gridView;
    int status, soBan;
    String tblID;
    List<Food> list;
    ArrayList<Order> foodList;
    Order_Sub_1_Adapter adapter;
    FirebaseFirestore db;
    int foodIDs;
    EditText etSoluong, etGhichu;
    TextView txtFoodName;
    int orderID, orderNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__sub_1);

        final Intent intent = this.getIntent();

        tblID = intent.getStringExtra("tblID");
        soBan = intent.getIntExtra("tbNum",0);
        status = intent.getIntExtra("status", 0);
        orderID = intent.getIntExtra("orderID", 0);
        orderNumber = intent.getIntExtra("orderNumber", 0);
        documentID = intent.getStringExtra("DocumentID");

        db = FirebaseFirestore.getInstance();
        gridView = findViewById(R.id.choose_food_grview);

        db.collection("Food")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                Food food = snapshot.toObject(Food.class);
                                list.add(food);
                            }
                            adapter = new Order_Sub_1_Adapter(getApplicationContext(), list);
                            gridView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "onComplete: Load food data fail " + task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Load data fail" + e);
                    }
                });

        foodList = new ArrayList<>();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Food food = (Food) parent.getItemAtPosition(position);
                foodIDs = food.getFoodID();

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View view2 = inflater.inflate(R.layout.dialog_add_food_in_order, null);

                etSoluong = view2.findViewById(R.id.edtSoluong);
                etGhichu = view2.findViewById(R.id.edtGhichu);
                txtFoodName = view2.findViewById(R.id.txtFoodName);
                txtFoodName.setText(food.getFoodName());

                AlertDialog.Builder builder = new AlertDialog.Builder(Order_Sub_1.this);
                builder.setTitle("Gọi món")
                        .setView(view2)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @SuppressLint("ShowToast")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int sl = Integer.parseInt(etSoluong.getText().toString().trim());
                                String note = etGhichu.getText().toString().trim();
                                Date now = new Date(System.currentTimeMillis());

                                final Order orderDetails = new Order(orderID,sl,now,note,foodIDs,1,0,tblID);
                                foodList.add(orderDetails);
                                orderID++;

                                data = new HashMap<>();
                                data.put("DocumentID",documentID);
                                data.put("SoMonDuocGoi",orderID);
                                data.put("FoodList", foodList);

                                db.collection("Order").document(documentID)
                                        .set(data, SetOptions.merge())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(),
                                                        "Đã gọi "+orderDetails.getOrderNumber()+" "
                                                                + food.getFoodUnit() +" "+ food.getFoodName()
                                                                + " cho bàn " + soBan,
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: " + e.getMessage());
                                            }
                                        });
                                db.collection("Table")
                                        .document(tblID)
                                        .update("status",1,
                                                "DocumentID",documentID)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Add order success");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: " + e.getMessage());
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}