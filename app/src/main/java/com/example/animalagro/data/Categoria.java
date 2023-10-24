package com.example.animalagro.data;

import com.google.gson.annotations.SerializedName;

public class Categoria {
    @SerializedName("id")
    private int id;

    @SerializedName("catNombre")
    private String catNombre;

    private String imagen;
    public Categoria(int id, String catNombre, String imagen) {
        this.id = id;
        this.catNombre = catNombre;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatNombre() {
        return catNombre;
    }

    public void setCatNombre(String catNombre) {
        this.catNombre = catNombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
