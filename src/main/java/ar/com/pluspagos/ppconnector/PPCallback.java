package ar.com.pluspagos.ppconnector;

import ar.com.pluspagos.ppconnector.models.Response;

public interface PPCallback {

    void onFinished(Response response);

}
