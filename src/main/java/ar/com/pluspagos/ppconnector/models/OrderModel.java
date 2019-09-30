package ar.com.pluspagos.ppconnector.models;

import com.google.gson.annotations.SerializedName;

public class OrderModel {

    private String idTransaccionInterno;
    private String urlNotificacion;
    //private double montoTotal;
    private String productos;
    @SerializedName("Fixed_Amount")
    private boolean fixedAmount;
    private String hash;
    private String montoTotal;

    public OrderModel() {
    }

    public String getIdTransaccionInterno() {
        return idTransaccionInterno;
    }

    public void setIdTransaccionInterno(String idTransaccionInterno) {
        this.idTransaccionInterno = idTransaccionInterno;
    }

    public String getUrlNotificacion() {
        return urlNotificacion;
    }

    public void setUrlNotificacion(String urlNotificacion) {
        this.urlNotificacion = urlNotificacion;
    }

    //public double getMontoTotal() {
    //    return montoTotal;
    //}
    //
    //public void setMontoTotal(double montoTotal) {
    //    this.montoTotal = montoTotal;
    //}

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public boolean isFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(boolean fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        this.montoTotal = montoTotal;
    }
}
