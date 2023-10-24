package com.example.animalagro.data;

public class Cita {
    private int id;
    private String CitaEstado;
    private String CitafechaHora;
    private String urlPdfHistoria;
    private String mascotaNombre;
    private String servicioNombre;


    public Cita(int id, String citaEstado, String citafechaHora, String urlPdfHistoria, String mascotaNombre, String servicioNombre) {
        this.id = id;
        this.CitaEstado = citaEstado;
        this.CitafechaHora = citafechaHora;
        this.urlPdfHistoria = urlPdfHistoria;
        this.mascotaNombre = mascotaNombre;
        this.servicioNombre = servicioNombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCitaEstado() {
        return CitaEstado;
    }

    public void setCitaEstado(String citaEstado) {
        this.CitaEstado = citaEstado;
    }

    public String getCitafechaHora() {
        return CitafechaHora;
    }

    public void setCitafechaHora(String citafechaHora) {
        this.CitafechaHora = citafechaHora;
    }

    public String getUrlPdfHistoria() {
        return urlPdfHistoria;
    }

    public void setUrlPdfHistoria(String urlPdfHistoria) {
        this.urlPdfHistoria = urlPdfHistoria;
    }

    public String getMascotaNombre() {
        return mascotaNombre;
    }

    public void setMascotaNombre(String mascotaNombre) {
        this.mascotaNombre = mascotaNombre;
    }

    public String getServicioNombre() {
        return servicioNombre;
    }

    public void setServicioNombre(String servicioNombre) {
        this.servicioNombre = servicioNombre;
    }
}

