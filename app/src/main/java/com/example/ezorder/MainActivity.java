package com.example.ezorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView txtOrder, txtFood, txtChef;
    String role, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userName = intent.getStringExtra("UserName");
        role = intent.getStringExtra("RoleName");

        txtOrder = findViewById(R.id.txtorder);
        txtFood = findViewById(R.id.txtfood);
        txtChef = findViewById(R.id.txtChef);

        Toast.makeText(getApplicationContext(),"Chào mừng "+ userName + " đăng nhập vào hệ thống!",Toast.LENGTH_SHORT).show();

        txtOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!role.equals("Chef")){
                    startActivity(new Intent(MainActivity.this, OrderScreen.class));
                } else {
                    Toast.makeText(getApplicationContext(),"Bạn không có quyền truy cập chức năng này!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (role.equals("Admin")){
                    startActivity(new Intent(MainActivity.this, ManagementScreen.class));
                } else {
                    Toast.makeText(getApplicationContext(),"Bạn không có quyền truy cập chức năng này!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!role.equals("Order")){
                    startActivity(new Intent(MainActivity.this, ChefScreen.class));
                } else {
                    Toast.makeText(getApplicationContext(),"Bạn không có quyền truy cập chức năng này!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}