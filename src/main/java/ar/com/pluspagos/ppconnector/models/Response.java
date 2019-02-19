package ar.com.pluspagos.ppconnector.models;

import okhttp3.ResponseBody;

import java.io.IOException;
import java.net.HttpURLConnection;

public class Response {

    private boolean status;
    private int code;
    private String message;
    private String data;

    public static Response fromException(Exception e) {
        Response r = new Response();
        r.status = false;
        r.code = 0;
        r.message = e.getLocalizedMessage();
        return r;
    }

    public static Response forbidden() {
        Response r = new Response();
        r.status = false;
        r.code = HttpURLConnection.HTTP_FORBIDDEN;
        r.data = "";
        r.message = "El servidor rechaz\u00f3 la conexi\u00f3n con el servicio.";
        return r;
    }

    public static Response copy(Response response) {
        Response r = new Response();
        r.status = response.status;
        r.code = response.code;
        r.data = response.data;
        r.message = response.data;
        return r;
    }

    public static Response fromError(ResponseBody errorBody) throws IOException {
        Response r = new Response();
        r.status = false;
        r.code = 0;
        r.message = errorBody.string();
        return r;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
