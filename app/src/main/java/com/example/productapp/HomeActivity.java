package com.example.productapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button addProductButton, viewProductsButton, logoutButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);

        addProductButton = findViewById(R.id.addProductButton);
        viewProductsButton = findViewById(R.id.viewProductsButton);
        logoutButton = findViewById(R.id.logoutButton);

        addProductButton.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AddProductActivity.class)));
        viewProductsButton.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ViewProductsActivity.class)));
        logoutButton.setOnClickListener(v -> logoutUser());
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.putString("email", null);
        editor.apply();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }
}
