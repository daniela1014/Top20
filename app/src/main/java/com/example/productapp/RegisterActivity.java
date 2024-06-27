package com.example.productapp;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button registerButton;
    private DatabaseHelper dbHelper;
    private boolean isPasswordVisible = false;
    private ImageView togglePasswordVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        togglePasswordVisibility = findViewById(R.id.togglePasswordVisibility);

        registerButton.setOnClickListener(v -> registerUser());
        togglePasswordVisibility.setOnClickListener(v -> togglePasswordVisibility());
    }

    private void registerUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (dbHelper.addUser(email, password)) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
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
