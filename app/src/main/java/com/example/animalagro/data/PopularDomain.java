package com.example.animalagro.data;

import java.io.Serializable;

public class PopularDomain implements Serializable {
    private int id;
    private String proNombre;
    private String proEstado;
    private double proPrecio;
    private String proFoto;
    private int numberinCart;
    private int proCategoria;

    private String proDescripcion;
    private int proCantidad;

    public PopularDomain(int id, String proNombre, String proEstado, double proPrecio, String proFoto, String proDescripcion, int proCategoria, int proCantidad) {
        this.id = id;
        this.proNombre = proNombre;
        this.proEstado = proEstado;
        this.proPrecio = proPrecio;
        this.proFoto = proFoto;
        this.proDescripcion = proDescripcion;
        this.proCategoria = proCategoria;
        this.proCantidad = proCantidad;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProEstado() {
        return proEstado;
    }

    public void setProEstado(String proEstado) {
        this.proEstado = proEstado;
    }

    public double getProPrecio() {
        return proPrecio;
    }

    public void setProPrecio(double proPrecio) {
        this.proPrecio = proPrecio;
    }

    public String getProFoto() {
        if (proFoto != null && !proFoto.isEmpty()) {
            if (!proFoto.startsWith("https://") && !proFoto.startsWith("http://")) {
                String baseUrl = "https://veterinariaanimalagro.pythonanywhere.com";
                return baseUrl + proFoto;
            }
        }
        return proFoto;
    }

    public void setProFoto(String proFoto) {
        this.proFoto = proFoto;
    }

    public String getProDescripcion() {
        return proDescripcion;
    }

    public void setProDescripcion(String proDescripcion) {
        this.proDescripcion = proDescripcion;
    }

    public int getNumberinCart() {
        return numberinCart;
    }

    public void setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
    }

    public int getProCategoria() {
        return proCategoria;
    }

    public void setProCategoria(int proCategoria) {
        this.proCategoria = proCategoria;
    }

    public int getProCantidad() {
        return proCantidad;
    }

    public void setProCantidad(int proCantidad) {
        this.proCantidad = proCantidad;
    }
}
