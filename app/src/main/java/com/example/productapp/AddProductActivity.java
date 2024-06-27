package com.example.productapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddProductActivity extends AppCompatActivity {
    private EditText productNameEditText, productPriceEditText;
    private Button saveProductButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poduct);

        dbHelper = new DatabaseHelper(this);

        productNameEditText = findViewById(R.id.productNameEditText);
        productPriceEditText = findViewById(R.id.productPriceEditText);
        saveProductButton = findViewById(R.id.saveProductButton);

        saveProductButton.setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {
        String name = productNameEditText.getText().toString();
        double price;
        try {
            price = Double.parseDouble(productPriceEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.addProduct(name, price)) {
            Toast.makeText(this, "Producto agregado: " + name + " - $" + price, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
        }
    }
}