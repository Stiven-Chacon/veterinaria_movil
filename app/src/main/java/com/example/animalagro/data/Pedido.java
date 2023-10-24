package com.example.animalagro.data;

public class Pedido {
    private int id;
    private String peEstado;
    private double peTotalPedido;
    private String peCodigoPedido;
    private String peFecha;
    private String urlPdfPedido;

    public Pedido(int id, String peEstado, double peTotalPedido, String peCodigoPedido, String peFecha, String urlPdfPedido) {
        this.id = id;
        this.peEstado = peEstado;
        this.peTotalPedido = peTotalPedido;
        this.peCodigoPedido = peCodigoPedido;
        this.peFecha = peFecha;
        this.urlPdfPedido = urlPdfPedido;
    }

    public int getId() {
        return id;
    }

    public String getPeEstado() {
        return peEstado;
    }

    public double getPeTotalPedido() {
        return peTotalPedido;
    }

    public String getPeCodigoPedido() {
        return peCodigoPedido;
    }

    public String getPeFecha() {
        return peFecha;
    }

    public String getUrlPdfPedido() {
        return urlPdfPedido;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPeEstado(String peEstado) {
        this.peEstado = peEstado;
    }

    public void setPeTotalPedido(double peTotalPedido) {
        this.peTotalPedido = peTotalPedido;
    }

    public void setPeCodigoPedido(String peCodigoPedido) {
        this.peCodigoPedido = peCodigoPedido;
    }

    public void setPeFecha(String peFecha) {
        this.peFecha = peFecha;
    }

    public void setUrlPdfPedido(String urlPdfPedido) {
        this.urlPdfPedido = urlPdfPedido;
    }
}

