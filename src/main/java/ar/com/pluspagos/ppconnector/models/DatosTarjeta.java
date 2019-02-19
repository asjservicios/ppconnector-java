package ar.com.pluspagos.ppconnector.models;

import com.google.gson.annotations.SerializedName;

public class DatosTarjeta {
    private String numeroTarjeta;
    private String titularTarjeta;
    private String codigoTarjeta;
    @SerializedName("a\u00f1oVencimiento")
    private String anoVencimiento;
    private String mesVencimiento;
    private String tipoDocumento;
    private String documentoTitular;
    private String fechaNacimientoTitular;
    private String numeroPuertaResumen;
    private String email;

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public String getCodigoTarjeta() {
        return codigoTarjeta;
    }

    public void setCodigoTarjeta(String codigoTarjeta) {
        this.codigoTarjeta = codigoTarjeta;
    }

    public String getAnoVencimiento() {
        return anoVencimiento;
    }

    public void setAnoVencimiento(String anoVencimiento) {
        this.anoVencimiento = anoVencimiento;
    }

    public String getMesVencimiento() {
        return mesVencimiento;
    }

    public void setMesVencimiento(String mesVencimiento) {
        this.mesVencimiento = mesVencimiento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumentoTitular() {
        return documentoTitular;
    }

    public void setDocumentoTitular(String documentoTitular) {
        this.documentoTitular = documentoTitular;
    }

    public String getFechaNacimientoTitular() {
        return fechaNacimientoTitular;
    }

    public void setFechaNacimientoTitular(String fechaNacimientoTitular) {
        this.fechaNacimientoTitular = fechaNacimientoTitular;
    }

    public String getNumeroPuertaResumen() {
        return numeroPuertaResumen;
    }

    public void setNumeroPuertaResumen(String numeroPuertaResumen) {
        this.numeroPuertaResumen = numeroPuertaResumen;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
