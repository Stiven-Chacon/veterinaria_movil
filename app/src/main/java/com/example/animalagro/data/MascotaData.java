package com.example.animalagro.data;

public class MascotaData {
    private int id;
    private String tipo;
    private String raza;
    private String nombre;
    private String foto;

    public MascotaData(int id, String tipo, String raza, String nombre, String foto) {
        this.id = id;
        this.tipo = tipo;
        this.raza = raza;
        this.nombre = nombre;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRaza() {
        return raza;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
