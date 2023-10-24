package com.example.animalagro.data;

public class PedidoPendienteAdmin {
    private String peFecha;
    private String peCodigo;
    private String urlPedido;

    public PedidoPendienteAdmin(String peFecha, String peCodigo, String urlPedido) {
        this.peFecha = peFecha;
        this.peCodigo = peCodigo;
        this.urlPedido = urlPedido;
    }

    public String getPeFecha() {
        return peFecha;
    }

    public void setPeFecha(String peFecha) {
        this.peFecha = peFecha;
    }

    public String getPeCodigo() {
        return peCodigo;
    }

    public void setPeCodigo(String peCodigo) {
        this.peCodigo = peCodigo;
    }

    public String getUrlPedido() {
        return urlPedido;
    }

    public void setUrlPedido(String urlPedido) {
        this.urlPedido = urlPedido;
    }
}
