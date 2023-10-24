package com.example.animalagro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.animalagro.data.PopularDomain;
import com.example.animalagro.Helper.ManagmentCart;
import com.example.animalagro.R;

public class DetailsActivity2 extends AppCompatActivity {
    private Button btncomprar;
    private TextView titleTxt, feeTxt, descriptionTxt, reviewTxt, scoreTxt;
    private ImageView picItem, backBtn;
    private PopularDomain object;
    private int numberOrder = 1;
    private ManagmentCart managmentCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);

        managmentCart = new ManagmentCart(this); // Inicializo el objeto managmentCart


        initView();
        getBundle();
    }
    private void getBundle(){
        object = (PopularDomain) getIntent().getSerializableExtra("object");

        Glide.with(this)
                .load(object.getProFoto())
                .into(picItem);

        titleTxt.setText(String.valueOf(object.getProNombre()));
        feeTxt.setText("$" + String.valueOf(object.getProPrecio()));
        descriptionTxt.setText(object.getProDescripcion());


        btncomprar.setOnClickListener(v -> {
            object.setNumberinCart(numberOrder);
            managmentCart.insertFood(object);
        });

        backBtn.setOnClickListener(v -> finish());

    }
    private void initView(){
        btncomprar = findViewById(R.id.btncomprar);
        feeTxt = findViewById(R.id.priceTxt);
        titleTxt = findViewById(R.id.titleTxt);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        picItem =findViewById(R.id.itemPic);
        backBtn =findViewById(R.id.backBtn);

    }
}