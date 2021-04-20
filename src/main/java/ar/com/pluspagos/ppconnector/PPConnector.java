package ar.com.pluspagos.ppconnector;

import ar.com.pluspagos.ppconnector.clients.RestClient;
import ar.com.pluspagos.ppconnector.models.Body;
import ar.com.pluspagos.ppconnector.models.CajaModel;
import ar.com.pluspagos.ppconnector.models.OrderModel;
import ar.com.pluspagos.ppconnector.models.PaymentModel;
import ar.com.pluspagos.ppconnector.models.Response;
import ar.com.pluspagos.ppconnector.models.TokenModel;
import ar.com.pluspagos.ppconnector.security.Package;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.IOException;
import java.net.HttpURLConnection;

public class PPConnector {

    public static void init(Ambiente ambiente, String guid, String frase) {
        RestClient.init(ambiente, guid, frase);
    }

    private static class GenericCallback implements Callback<Response> {

        private PPCallback ppCallback;

        public GenericCallback(PPCallback ppCallback) {
            this.ppCallback = ppCallback;
        }

        @Override
        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
            try {
                ppCallback.onFinished(processResponse(response));
            } catch (IOException e) {
                ppCallback.onFinished(Response.fromException(e));
            }
        }

        @Override
        public void onFailure(Call<Response> call, Throwable t) {
            ppCallback.onFinished(Response.fromException((Exception) t));
        }

    };

    public static void healthCheck(PPCallback callback) {
        RestClient.getHealthChecks().getHealthCheck().enqueue(new GenericCallback(callback));
    }

    public static void getPaymentToken(TokenModel paymentTokenData, String secretKey, PPCallback callback) {
        RestClient.getTokens().getPaymentToken(
                "Bearer " + RestClient.getAccessToken(),
                Body.with(Package.getPackage(paymentTokenData, secretKey))
        ).enqueue(new GenericCallback(callback));
    }

    public static void executePayment(PaymentModel paymentData, String paymentToken, String secretKey, PPCallback callback) {
        RestClient.getPayments().executePayment(
                "Bearer " + RestClient.getAccessToken(),
                paymentToken,
                Body.with(Package.getPackage(paymentData, secretKey, paymentToken, true))
        ).enqueue(new GenericCallback(callback));
    }

    public static void getPaymentMethods(PPCallback callback) {
        RestClient.getPayments().getPaymentMethods(
                "Bearer " + RestClient.getAccessToken()
        ).enqueue(new GenericCallback(callback));
    }

    public static void getPaymentMethodsAgrupador(String ente, PPCallback callback) {
        RestClient.getPayments().getPaymentMethodsAgrupador(
                "Bearer " + RestClient.getAccessToken(),
                ente
        ).enqueue(new GenericCallback(callback));
    }

    public static void getTransactions(PPCallback callback) {
        RestClient.getQuerys().getTransactions(
                "Bearer " + RestClient.getAccessToken()
        ).enqueue(new GenericCallback(callback));
    }

    public static void getTransactionByTxComercioId(String transaccionComercioId, PPCallback callback) {
        RestClient.getQuerys().getTransactionByTxComercioId(
                "Bearer " + RestClient.getAccessToken(),
                transaccionComercioId
        ).enqueue(new GenericCallback(callback));
    }

    private static Response processResponse(retrofit2.Response<Response> response) throws IOException {
        if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
            return Response.forbidden();
        } else {
            Response body = response.body();
            if (body != null)
                return Response.copy(response.body());
            else
                return Response.fromError(response.errorBody());
        }
    }

    public static void caja(CajaModel cajaModel, String secretKey, PPCallback callback) {
        RestClient.getCajas().caja(
                "Bearer " + RestClient.getAccessToken(),
                Body.with(Package.getPackage(cajaModel, secretKey))
        ).enqueue(new GenericCallback(callback));
    }

    public static void order(OrderModel order, String secretKey, String codigo, String ttlPreference, PPCallback callback) {
        RestClient.getOrders().createOrder(
                "Bearer " + RestClient.getAccessToken(),
                ttlPreference,
                Body.with(Package.getPackage(order, secretKey)),
                codigo
        ).enqueue(new GenericCallback(callback));
    }


    public static void getOrder(String codigo, PPCallback callback) {
        RestClient.getOrders().getOrder(
                "Bearer " + RestClient.getAccessToken(),
                codigo
        ).enqueue(new GenericCallback(callback));
    }

    public static void getOrderByCajaId(int cajaId, PPCallback callback) {
        RestClient.getOrders().getOrderByCajaId(
                "Bearer " + RestClient.getAccessToken(),
                cajaId
        ).enqueue(new GenericCallback(callback));
    }


    public static void deleteOrder(String codigo, PPCallback callback) {
        RestClient.getOrders().deleteOrder(
                "Bearer " + RestClient.getAccessToken(),
                codigo
        ).enqueue(new GenericCallback(callback));
    }

}
