package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.animalagro.Adapter.CartListAdapter;
import com.example.animalagro.Helper.ChangeNumberItemsListener;
import com.example.animalagro.Helper.ManagmentCart;
import com.example.animalagro.R;
import com.example.animalagro.data.PopularDomain;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private  RecyclerView recyclerView;
    private ManagmentCart managmentCart;
    private TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emplyTxt;
    private double tax;
    private ScrollView scrollView;
    private ImageView backBtn;
    private AppCompatButton btnOrdenar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managmentCart=new ManagmentCart(this);

        initView();
        setVariavle();
        calcuaIteCart();
        initList();

        // botón Ordenar
        btnOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOrderButtonClick();
            }
        });

    }

    private void initList() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new CartListAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calcuaIteCart();
            }
        });
        recyclerView.setAdapter(adapter);
        if (managmentCart.getListCart().isEmpty()) {
            emplyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            emplyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    private void calcuaIteCart() {
        double percentTax = 0.19; //Impuesto
        double delivery = 15000; // Costo de envio

        // Calcular el impuesto (IVA) aplicando el porcentaje al costo total de los elementos en el carrito
        tax = Math.round((managmentCart.getTotalFree() * percentTax * 100.0))/100.0;

        double total = Math.round((managmentCart.getTotalFree()  + delivery) * 100)/100;
        double itemTotal = Math.round(managmentCart.getTotalFree()*100)/100;

        totalFeeTxt.setText("$"+itemTotal);
        taxTxt.setText("$"+tax);
        deliveryTxt.setText("$"+delivery);
        totalTxt.setText("$" +total);
    }

    private void setVariavle() {
        backBtn.setOnClickListener(v -> finish());
    }

    private void initView() {
        totalFeeTxt= findViewById(R.id.totalFeeTxt);
        taxTxt= findViewById(R.id.taxTxt);
        deliveryTxt= findViewById(R.id.deliveryTxt);
        totalTxt= findViewById(R.id.totalTxt);
        recyclerView= findViewById(R.id.view3);
        scrollView= findViewById(R.id.scrollView3);
        backBtn= findViewById(R.id.backBtn);
        emplyTxt = findViewById(R.id.emplyTxt);
        btnOrdenar = findViewById(R.id.btnOrdenar);

    }

    private void onOrderButtonClick() {
        // Verificar si el usuario está logeado
        if (isUserLoggedIn()) {
            // Obtener datos del usuario y productos seleccionados
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("id", 0);
            String firstName = sharedPreferences.getString("first_name", "");
            String lastName = sharedPreferences.getString("last_name", "");
            String email = sharedPreferences.getString("email", "");
            String userNoDoc = sharedPreferences.getString("userNoDoc", "");
            String userTelefono = sharedPreferences.getString("userTelefono", "");

            //precio y datos de los valores
            String totalFee = totalFeeTxt.getText().toString();
            String tax = taxTxt.getText().toString();
            String delivery = deliveryTxt.getText().toString();
            String total = totalTxt.getText().toString();

            // Obtener productos seleccionados
            ArrayList<PopularDomain> selectedProducts = managmentCart.getListCart();

            Intent orderIntent = new Intent(this, PasarelaDePagoActivity.class);
            orderIntent.putExtra("userId", userId);
            orderIntent.putExtra("firstName", firstName);
            orderIntent.putExtra("lastName", lastName);
            orderIntent.putExtra("email", email);
            orderIntent.putExtra("userNoDoc", userNoDoc);
            orderIntent.putExtra("userTelefono", userTelefono);
            orderIntent.putExtra("totalFee", totalFee);
            orderIntent.putExtra("tax", tax);
            orderIntent.putExtra("delivery", delivery);
            orderIntent.putExtra("total", total);

            // Pasar la lista de productos seleccionados a la actividad de la pasarela de pago
            orderIntent.putExtra("selectedProducts", selectedProducts);

            startActivity(orderIntent);
        } else {
            showLoginRequiredDialog();
        }
    }

    private void showLoginRequiredDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Iniciar Sesión Requerido")
                .setMessage("Debes iniciar sesión para realizar esta acción.")
                .setPositiveButton("Iniciar Sesión", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent loginIntent = new Intent(CartActivity.this, UserActivity.class); // Cambia 'context' a 'CartActivity.this'
                        startActivity(loginIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    // Método para verificar si el usuario está logeado
    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        return sharedPreferences.contains("id"); // Verifica si el ID del usuario está presente en SharedPreferences
    }
}