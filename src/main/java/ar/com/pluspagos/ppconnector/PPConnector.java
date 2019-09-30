package ar.com.pluspagos.ppconnector;

import ar.com.pluspagos.ppconnector.clients.RestClient;
import ar.com.pluspagos.ppconnector.models.Body;
import ar.com.pluspagos.ppconnector.models.CajaModel;
import ar.com.pluspagos.ppconnector.models.OrderModel;
import ar.com.pluspagos.ppconnector.models.PaymentModel;
import ar.com.pluspagos.ppconnector.models.Response;
import ar.com.pluspagos.ppconnector.models.TokenModel;
import ar.com.pluspagos.ppconnector.security.Package;

import java.io.IOException;
import java.net.HttpURLConnection;

public class PPConnector {

    public static void init(Ambiente ambiente, String guid, String frase) {
        RestClient.init(ambiente, guid, frase);
    }

    public static Response healthCheck() {
        try {
            return processResponse(RestClient.getHealthChecks().getHealthCheck().execute());
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }

    public static Response getPaymentToken(TokenModel paymentTokenData, String secretKey) {
        try {
            return processResponse(
                    RestClient.getTokens().getPaymentToken(
                            "Bearer " + RestClient.getAccessToken(),
                            Body.with(Package.getPackage(paymentTokenData, secretKey))
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }

    public static Response executePayment(PaymentModel paymentData, String paymentToken, String secretKey) {
        try {
            return processResponse(
                    RestClient.getPayments().executePayment(
                            "Bearer " + RestClient.getAccessToken(),
                            paymentToken,
                            Body.with(Package.getPackage(paymentData, secretKey, paymentToken, true))
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }

    public static Response getPaymentMethods() {
        try {
            return processResponse(
                    RestClient.getPayments().getPaymentMethods(
                            "Bearer " + RestClient.getAccessToken()
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }

    public static Response getPaymentMethodsAgrupador(String ente) {
        try {
            return processResponse(
                    RestClient.getPayments().getPaymentMethodsAgrupador(
                            "Bearer " + RestClient.getAccessToken(),
                            ente
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }

    public static Response getTransactions() {
        try {
            return processResponse(
                    RestClient.getQuerys().getTransactions(
                            "Bearer " + RestClient.getAccessToken()
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }

    public static Response getTransactionByTxComercioId(String transaccionComercioId) {
        try {
            return processResponse(
                    RestClient.getQuerys().getTransactionByTxComercioId(
                            "Bearer " + RestClient.getAccessToken(),
                            transaccionComercioId
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
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

    public static Response caja(CajaModel cajaModel, String secretKey) {
        try {
            return processResponse(
                    RestClient.getCajas().caja(
                            "Bearer " + RestClient.getAccessToken(),
                            Body.with(Package.getPackage(cajaModel, secretKey))
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }

    public static Response order(OrderModel order, String secretKey, String codigo, String ttlPreference) {
        try {
            return processResponse(
                    RestClient.getOrders().createOrder(
                            "Bearer " + RestClient.getAccessToken(),
                            ttlPreference,
                            Body.with(Package.getPackage(order, secretKey)),
                            codigo
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }


    public static Response getOrder(String codigo) {
        try {
            return processResponse(
                    RestClient.getOrders().getOrder(
                            "Bearer " + RestClient.getAccessToken(),
                            codigo
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }

    public static Response getOrderByCajaId(int cajaId) {
        try {
            return processResponse(
                    RestClient.getOrders().getOrderByCajaId(
                            "Bearer " + RestClient.getAccessToken(),
                            cajaId
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }


    public static Response deleteOrder(String codigo) {
        try {
            return processResponse(
                    RestClient.getOrders().deleteOrder(
                            "Bearer " + RestClient.getAccessToken(),
                            codigo
                    ).execute()
            );
        } catch (IOException e) {
            return Response.fromException(e);
        }
    }

}
