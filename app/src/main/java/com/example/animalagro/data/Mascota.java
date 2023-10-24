package com.example.animalagro.data;

import com.google.gson.annotations.SerializedName;

public class Mascota {
    @SerializedName("id")
    private int id;
    @SerializedName("masNombre")
    private String nombre;

    @SerializedName("masFoto")
    private String fotoUrl;

    @SerializedName("masRaza")
    private String raza;

    @SerializedName("masTipoAnimal")
    private String tipoAnimal;

    public Mascota(int id, String nombre, String fotoUrl, String raza, String tipoAnimal) {
        this.id = id;
        this.nombre = nombre;
        this.fotoUrl = fotoUrl;
        this.raza = raza;
        this.tipoAnimal = tipoAnimal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getFotoUrlCompleta(String baseUrl) {
        return baseUrl + fotoUrl;
    }
}
