package com.example.ezorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ezorder.Fragment.FoodFragment;
import com.example.ezorder.Fragment.TableFragment;
import com.example.ezorder.Fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManagementScreen extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_screen);

        bottomNavigationView = findViewById(R.id.bottomMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.menu_user: {
                        fragment = new UserFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.menu_food: {
                        fragment = new FoodFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    case R.id.menu_table: {
                        fragment = new TableFragment();
                        loadFragment(fragment);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

}