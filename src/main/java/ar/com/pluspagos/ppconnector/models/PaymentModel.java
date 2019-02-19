package ar.com.pluspagos.ppconnector.models;

public class PaymentModel {
    private DatosTarjeta datosTarjeta;
    private int medioPagoId;
    private int cantidadCuotas;
    private boolean aceptaHabeasData;
    private boolean aceptTerminosyCondiciones;
    private String ipCliente;
    private String hash;

    public DatosTarjeta getDatosTarjeta() {
        return datosTarjeta;
    }

    public void setDatosTarjeta(DatosTarjeta datosTarjeta) {
        this.datosTarjeta = datosTarjeta;
    }

    public int getMedioPagoId() {
        return medioPagoId;
    }

    public void setMedioPagoId(int medioPagoId) {
        this.medioPagoId = medioPagoId;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public boolean isAceptaHabeasData() {
        return aceptaHabeasData;
    }

    public void setAceptaHabeasData(boolean aceptaHabeasData) {
        this.aceptaHabeasData = aceptaHabeasData;
    }

    public boolean isAceptTerminosyCondiciones() {
        return aceptTerminosyCondiciones;
    }

    public void setAceptTerminosyCondiciones(boolean aceptTerminosyCondiciones) {
        this.aceptTerminosyCondiciones = aceptTerminosyCondiciones;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public void setIpCliente(String ipCliente) {
        this.ipCliente = ipCliente;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
