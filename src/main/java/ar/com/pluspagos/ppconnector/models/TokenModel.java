package ar.com.pluspagos.ppconnector.models;

public class TokenModel {

    private String comercio;
    private String urlDominio;
    private String[] productos;
    private String totalOperacion;
    private String sucursalComercio;
    private String transaccionComercioId;
    private String hash;
    private String ip;

    public String getComercio() {
        return comercio;
    }

    public void setComercio(String comercio) {
        this.comercio = comercio;
    }

    public String getUrlDominio() {
        return urlDominio;
    }

    public void setUrlDominio(String urlDominio) {
        this.urlDominio = urlDominio;
    }

    public String[] getProductos() {
        return productos;
    }

    public void setProductos(String[] productos) {
        this.productos = productos;
    }

    public String getTotalOperacion() {
        return totalOperacion;
    }

    public void setTotalOperacion(String totalOperacion) {
        this.totalOperacion = totalOperacion;
    }

    public String getSucursalComercio() {
        return sucursalComercio;
    }

    public void setSucursalComercio(String sucursalComercio) {
        this.sucursalComercio = sucursalComercio;
    }

    public String getTransaccionComercioId() {
        return transaccionComercioId;
    }

    public void setTransaccionComercioId(String transaccionComercioId) {
        this.transaccionComercioId = transaccionComercioId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
