package com.example.productapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;
    private DatabaseHelper dbHelper;
    private SharedPreferences sharedPreferences;
    private boolean isPasswordVisible = false;
    private ImageView togglePasswordVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);

        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        togglePasswordVisibility.setOnClickListener(v -> togglePasswordVisibility());

        checkLoginStatus();
    }

    private void loginUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (dbHelper.checkUser(email, password)) {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putString("email", email);
            editor.apply();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLoginStatus() {
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            togglePasswordVisibility.setImageResource(R.drawable.ic_visibility_off);
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            togglePasswordVisibility.setImageResource(R.drawable.ic_visibility);
        }
        isPasswordVisible = !isPasswordVisible;
        passwordEditText.setSelection(passwordEditText.length());
    }
}
