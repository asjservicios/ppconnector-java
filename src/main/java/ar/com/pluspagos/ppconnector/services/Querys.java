package ar.com.pluspagos.ppconnector.services;

import ar.com.pluspagos.ppconnector.models.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface Querys {

    @GET("transactions")
    Call<Response> getTransactions(@Header("Authorization") String accessToken);

    @GET("transaction/{txComercioId}")
    Call<Response> getTransactionByTxComercioId(@Header("Authorization") String accessToken,
                                                @Path("txComercioId") String txComercioId);

}
