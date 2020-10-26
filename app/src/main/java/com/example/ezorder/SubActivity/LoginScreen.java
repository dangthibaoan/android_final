package com.example.ezorder.SubActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ezorder.MainActivity;
import com.example.ezorder.R;

public class LoginScreen extends AppCompatActivity {
    Button btnLogin;
    EditText eUsername, ePassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

//        eUsername = findViewById(R.id.username);
//        ePassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.buttonLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String name = eUsername.getText().toString().trim();
//                String pass = ePassword.getText().toString().trim();
//                User user = new User();
//                user.setAccountName(name);
//                user.setAccountPass(pass);

                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intent);
//                if(name.isEmpty() || pass.isEmpty()){
//                    Toast.makeText(LoginScreen.this, "Cần nhập đủ thông tin", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if(helper.checkUser(user)> 0 ){
//                    Intent intent = new Intent(LoginScreen.this, MainActivity.class);
//                    startActivity(intent);
//                }
            }
        });
    }
}