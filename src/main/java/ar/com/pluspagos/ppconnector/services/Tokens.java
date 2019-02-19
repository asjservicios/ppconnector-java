package ar.com.pluspagos.ppconnector.services;

import ar.com.pluspagos.ppconnector.models.AuthenticationModel;
import ar.com.pluspagos.ppconnector.models.Body;
import ar.com.pluspagos.ppconnector.models.Response;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Tokens {

    @POST("sesion")
    Call<Response> getAuthenticationToken(@retrofit2.http.Body AuthenticationModel authenticationModel);
    @POST("tokens")
    Call<Response> getPaymentToken(@Header("Authorization") String accessToken,
                                   @retrofit2.http.Body Body body);

}
