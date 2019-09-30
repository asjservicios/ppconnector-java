package ar.com.pluspagos.ppconnector.clients;

import ar.com.pluspagos.ppconnector.Ambiente;
import ar.com.pluspagos.ppconnector.models.AuthenticationModel;
import ar.com.pluspagos.ppconnector.models.Response;
import ar.com.pluspagos.ppconnector.services.Cajas;
import ar.com.pluspagos.ppconnector.services.HealthChecks;
import ar.com.pluspagos.ppconnector.services.Orders;
import ar.com.pluspagos.ppconnector.services.Payments;
import ar.com.pluspagos.ppconnector.services.Querys;
import ar.com.pluspagos.ppconnector.services.Tokens;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Route;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.net.HttpURLConnection;

public abstract class RestClient {

    private static String accessToken;

    private static Ambiente ambiente;
    private static AuthenticationModel authenticationModel;

    private static Retrofit retrofit;

    private static HealthChecks healthChecks;
    private static Tokens tokens;
    private static Payments payments;
    private static Querys querys;
    private static Cajas cajas;
    private static Orders orders;

    public static void init(Ambiente ambiente, String guid, String frase) {
        authenticationModel = AuthenticationModel.with(guid, frase);
        RestClient.ambiente = ambiente;
    }

    private static Retrofit get() {
        if (retrofit == null) {
            if (ambiente == null)
                throw new IllegalStateException("Connector no inicializado. Falta llamar a PPConnector.init()");

            TokenAuthenticator authenticator = new TokenAuthenticator();
            OkHttpClient client = new OkHttpClient.Builder()
                    .authenticator(authenticator).build();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(ambiente.getEndpoint())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            authenticator.setRetrofit(retrofit);
        }
        return retrofit;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static HealthChecks getHealthChecks() {
        if (healthChecks == null) healthChecks = get().create(HealthChecks.class);
        return healthChecks;
    }

    public static Tokens getTokens() {
        if (tokens == null) tokens = get().create(Tokens.class);
        return tokens;
    }

    public static Payments getPayments() {
        if (payments == null) payments = get().create(Payments.class);
        return payments;
    }

    public static Querys getQuerys() {
        if (querys == null) querys = get().create(Querys.class);
        return querys;
    }

    public static Cajas getCajas(){
        if(cajas == null) cajas = get().create(Cajas.class);
        return cajas;
    }

    public static Orders getOrders(){
        if(orders == null) orders = get().create(Orders.class);
        return orders;
    }

    private static class TokenAuthenticator implements Authenticator {

        private Retrofit retrofit;

        public void setRetrofit(Retrofit retrofit) {
            this.retrofit = retrofit;
        }

        @Override
        public Request authenticate(Route route, okhttp3.Response response) throws IOException {
            if (authenticationModel == null) {
                throw new IllegalStateException("Connector no inicializado. Falta llamar a PPConnector.init()");
            }

            retrofit2.Response<Response> tokenResponse = retrofit.create(Tokens.class)
                    .getAuthenticationToken(authenticationModel).execute();
            if (tokenResponse.code() == HttpURLConnection.HTTP_OK) {
                accessToken = tokenResponse.body().getData();

                return response.request().newBuilder()
                        .header("Authorization", "Bearer " + accessToken)
                        .build();
            }

            return null;
        }
    }

}
