package ar.com.pluspagos.ppconnector.services;

import ar.com.pluspagos.ppconnector.models.Response;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HealthChecks {

    @GET("health")
    Call<Response> getHealthCheck();

}
