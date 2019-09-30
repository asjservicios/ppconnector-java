package ar.com.pluspagos.ppconnector.services;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ar.com.pluspagos.ppconnector.models.Body;
import ar.com.pluspagos.ppconnector.models.Response;



public interface Cajas {

    @POST("caja")
    Call<Response> caja(@Header("Authorization") String accessToken, @retrofit2.http.Body Body body);

}
