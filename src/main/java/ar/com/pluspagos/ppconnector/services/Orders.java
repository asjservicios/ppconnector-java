package ar.com.pluspagos.ppconnector.services;

import ar.com.pluspagos.ppconnector.models.Body;
import ar.com.pluspagos.ppconnector.models.Response;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Orders {

    @POST("order/{codigo}")
    Call<Response> createOrder(@Header("Authorization") String accessToken,
                               @Header("X-Ttl-Preference") String ttlPreference,
                               @retrofit2.http.Body Body body,
                               @Path("codigo") String codigo);

    @GET("order/{codigo}")
    Call<Response> getOrder(@Header("Authorization") String accessToken, @Path("codigo") String codigo);

    @GET("caja/{cajaId}")
    Call<Response> getOrderByCajaId(@Header("Authorization") String accessToken, @Path("cajaId") int cajaId);

    @DELETE("order/{codigo}")
    Call<Response> deleteOrder(@Header("Authorization") String accessToken, @Path("codigo") String codigo);

}
