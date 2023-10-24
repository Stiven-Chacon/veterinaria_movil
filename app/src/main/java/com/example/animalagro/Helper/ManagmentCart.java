package com.example.animalagro.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.animalagro.data.PopularDomain;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;
    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB=new TinyDB(context);
    }
    public void insertFood(PopularDomain item) {
        ArrayList<PopularDomain> listPop = getListCart();
        boolean existAlready = false;
        int n = 0;

        for (int i = 0; i < listPop.size(); i++) {
            if (listPop.get(i).getProNombre().equals(item.getProNombre())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            // Calcular la cantidad que se puede agregar sin superar la cantidad disponible
            int availableQuantity = listPop.get(n).getProCantidad() - listPop.get(n).getNumberinCart();
            if (item.getNumberinCart() <= availableQuantity) {
                listPop.get(n).setNumberinCart(listPop.get(n).getNumberinCart() + item.getNumberinCart());
                Toast.makeText(context, "Producto Agregado Al Carrito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No se pueden agregar más unidades de este producto al carrito, cantidad disponible agotada.", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (item.getNumberinCart() <= item.getProCantidad()) {
                listPop.add(item);
                Toast.makeText(context, "Producto Agregado Al Carrito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "No se pueden agregar más unidades de este producto al carrito, cantidad disponible agotada.", Toast.LENGTH_SHORT).show();
            }
        }

        tinyDB.putListObject("CartList", listPop);
    }



    public ArrayList<PopularDomain> getListCart(){

        return tinyDB.getListObject("CartList");
    }

    public void minusNumberItem(ArrayList<PopularDomain>listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        if (listItem.get(position).getNumberinCart()==1) {
            listItem.remove(position);
        } else {
            listItem.get(position).setNumberinCart(listItem.get(position).getNumberinCart()-1);
        }
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
    public void plusNumberItem(ArrayList<PopularDomain> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        PopularDomain product = listItem.get(position);
        int currentQuantity = product.getNumberinCart();
        int availableQuantity = product.getProCantidad();

        if (currentQuantity < availableQuantity) {
            product.setNumberinCart(currentQuantity + 1);
            tinyDB.putListObject("CartList", listItem);
            changeNumberItemsListener.change();
        } else {
            Toast.makeText(context, "No se pueden agregar más unidades de este producto al carrito, cantidad disponible agotada.", Toast.LENGTH_SHORT).show();
        }
    }

    public Double getTotalFree(){
        ArrayList<PopularDomain> listItem = getListCart();
        double fee = 0;
        for (int i = 0; i < listItem.size(); i++) {
            fee = fee + (listItem.get(i).getProPrecio() * listItem.get(i).getNumberinCart());
        }
        return fee;
    }
}
