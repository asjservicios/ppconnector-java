package ar.com.pluspagos.ppconnector.models;

import com.google.gson.annotations.SerializedName;

public class CajaModel {

    private String nombre;
    private String codigo;
    private int sucursalComercioId;
    @SerializedName("Fixed_Amount")
    private boolean fixedAmount;
    private String hash;

    public CajaModel() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getSucursalComercioId() {
        return sucursalComercioId;
    }

    public void setSucursalComercioId(int sucursalComercioId) {
        this.sucursalComercioId = sucursalComercioId;
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

}
