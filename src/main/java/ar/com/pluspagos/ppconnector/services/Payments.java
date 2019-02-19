package ar.com.pluspagos.ppconnector.services;

import ar.com.pluspagos.ppconnector.models.Body;
import ar.com.pluspagos.ppconnector.models.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Payments {

    @POST("payment")
    Call<Response> executePayment(@Header("Authorization") String accessToken,
                                  @Header("X-Token") String paymentToken,
                                  @retrofit2.http.Body Body body);

    @GET("payment-methods")
    Call<Response> getPaymentMethods(@Header("Authorization") String accessToken);

    @GET("payment-methods-agrupador")
    Call<Response> getPaymentMethodsAgrupador(@Header("Authorization") String accessToken,
                                              @Query("Ente") String ente);

}
