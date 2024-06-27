package com.example.productapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewProductsActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private DatabaseHelper dbHelper;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = dbHelper.getAllProducts();
        if (productList != null) {
            productAdapter = new ProductAdapter(productList, this);
            recyclerView.setAdapter(productAdapter);
        } else {
            Toast.makeText(this, "No hay productos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(int position) {
        Product product = productList.get(position);
        showDeleteConfirmationDialog(product, position);
    }

    private void showDeleteConfirmationDialog(Product product, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Producto")
                .setMessage("¿Estás seguro de que quieres eliminar este producto?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    if (dbHelper.deleteProduct(product.getId())) {
                        productList.remove(position);
                        productAdapter.notifyItemRemoved(position);
                        Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
