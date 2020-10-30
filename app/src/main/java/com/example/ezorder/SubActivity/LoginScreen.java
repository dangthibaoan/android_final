package com.example.ezorder.SubActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ezorder.MainActivity;
import com.example.ezorder.Model.User;
import com.example.ezorder.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class LoginScreen extends AppCompatActivity {
    private static final String TAG = "Login";
    public static int USERID;

    Button btnLogin;
    TextInputEditText eUsername, ePassword;
    TextInputLayout uF, pF;
    String prefname="ezData", accountName, accountPass, role, name;
    FirebaseFirestore db;

    Boolean loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        eUsername = findViewById(R.id.username);
        ePassword = findViewById(R.id.password);
        uF = findViewById(R.id.usernameField);
        pF = findViewById(R.id.passwordField);
        btnLogin = findViewById(R.id.buttonLogin);

        loginStatus=false;
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    @Override
    protected void onPause() {
        savingPreferences();
        super.onPause();
    }

    @Override
    protected void onResume() {
        restoringPreferences();
        super.onResume();
    }

    // hàm đăng nhập
    public void doLogin(){
        accountName = eUsername.getText().toString();
        accountPass = ePassword.getText().toString();

        db = FirebaseFirestore.getInstance();
        db.collection("User")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                User user = snapshot.toObject(User.class);
                                if (accountName.equals(user.getAccountName()) && accountPass.equals(user.getAccountPass())){
                                    loginStatus=true;
                                    role = user.getUserRole();
                                    USERID = user.getUserID();
                                    name =user.getUserName();
                                    break;
                                }
                            }
                            if (!loginStatus) {
                                Toast.makeText(getApplicationContext(),"Sai tên đăng nhập hoặc mật khẩu!",Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent =new Intent(LoginScreen.this, MainActivity.class);
                                intent.putExtra("UserName", name);
                                intent.putExtra("RoleName", role);
                                startActivity(intent);
                            }
                        } else {
                            Log.d(TAG, "onComplete: Load data error " + task.getException());
                        }
                    }
                });
    }

    public void savingPreferences()
    {
        SharedPreferences pre = getSharedPreferences(prefname, MODE_PRIVATE);
        //tạo đối tượng Editor để lưu thay đổi
        SharedPreferences.Editor editor = pre.edit();
        String user = eUsername.getText().toString();
        String pwd = ePassword.getText().toString();
        editor.clear();
        editor.putString("user", user);
        editor.putString("pwd", pwd);
        editor.putString("role", role);
        editor.putBoolean("OK",true);
        //chấp nhận lưu xuống file
        editor.apply();
        editor.commit();

    }

    public void restoringPreferences()
    {
        SharedPreferences pre = getSharedPreferences(prefname, MODE_PRIVATE);
        //lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
        boolean bchk = pre.getBoolean("OK", false);
        if(bchk)
        {
            //lấy user, pwd, nếu không thấy giá trị mặc định là rỗng
            String user=pre.getString("user", "");
            String pwd=pre.getString("pwd", "");
            eUsername.setText(user);
            ePassword.setText(pwd);
        }
    }
}