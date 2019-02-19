package ar.com.pluspagos.ppconnector.models;

public class Body {

    private String body;

    public static Body with(String body) {
        Body b = new Body();
        b.body = body;
        return b;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
