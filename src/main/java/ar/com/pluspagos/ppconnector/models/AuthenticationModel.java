package ar.com.pluspagos.ppconnector.models;

public class AuthenticationModel {

    private String guid;
    private String frase;

    public static AuthenticationModel with(String guid, String frase) {
        AuthenticationModel authenticationModel = new AuthenticationModel();
        authenticationModel.guid = guid;
        authenticationModel.frase = frase;
        return authenticationModel;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }
}
