package ar.com.pluspagos.ppconnector;

public enum Ambiente {
    SANDBOX("https://sandboxpp.asjservicios.com.ar:8082/"),
    PRODUCTION("https://botonpp.asjservicios.com.ar:8082/"),
    TEST("http://boton.apipublica.dev.solucionesandinas.com.ar/");

    private final String endpoint;

    Ambiente(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
