package ar.com.pluspagos.ppconnector;

import ar.com.pluspagos.ppconnector.models.DatosTarjeta;
import ar.com.pluspagos.ppconnector.models.PaymentModel;
import ar.com.pluspagos.ppconnector.models.Response;
import ar.com.pluspagos.ppconnector.models.TokenModel;

import java.io.IOException;
import java.util.UUID;

public class Test {

    public static void main(String[] args) throws IOException {
        String comercio = "573cc80e-5916-47cd-8028-b288932052a0";
        String frase = "K74kjShQOdC6tnbPUocgRdZOT4QUo38QauCcULFYX14=";
        String secretKey = "WaveApps_77cf0de1-99be-4f43-893d-e5880edbf208";

        PPConnector.init(Ambiente.SANDBOX, comercio, frase);


        log("HealthCheck", PPConnector.healthCheck());


        /*
        OkHttpClient client = new OkHttpClient.Builder().build();
        String publicIp = client.newCall(new Request.Builder().url("http://checkip.amazonaws.com")
                .get().build()).execute().body().string().trim();
                */
        
        /******* getPaymentToken ******/
        String publicIp = "207.248.125.4";
        String urlDominio = "www.google.com";

        TokenModel tokenModel = new TokenModel();
        tokenModel.setComercio(comercio);
        tokenModel.setIp(publicIp);
        tokenModel.setUrlDominio(urlDominio);
        tokenModel.setSucursalComercio(null);
        tokenModel.setProductos(new String[] {"Producto 1", "Producto 2"});
        tokenModel.setTotalOperacion("200");
        tokenModel.setTransaccionComercioId(UUID.randomUUID().toString());
        
        Response resPT =  PPConnector.getPaymentToken(tokenModel, secretKey);
        log("GetPaymentToken", resPT);
        String paymentToken = resPT.getData();

        /******* getPaymentMethods ******/
        log("GetPaymentMethods", PPConnector.getPaymentMethods());
        
        /******* executePayment ******/
        System.out.println("******* executePayment ******");
        DatosTarjeta dt = new DatosTarjeta();
        dt.setAnoVencimiento("20");
        dt.setMesVencimiento("08");
        dt.setCodigoTarjeta("123");
        dt.setDocumentoTitular("22222222");
        dt.setEmail("email@gmail.com");
        dt.setFechaNacimientoTitular("12031993");
        dt.setNumeroPuertaResumen("20");
        dt.setNumeroTarjeta("4507990000004905");
        dt.setTipoDocumento("DNI");
        dt.setTitularTarjeta("JUAN PEREZ");
        
        PaymentModel pm = new PaymentModel();
        pm.setAceptaHabeasData(true);
        pm.setAceptTerminosyCondiciones(true);
        pm.setCantidadCuotas(1);
        pm.setIpCliente(publicIp);
        pm.setMedioPagoId(4);
        pm.setDatosTarjeta(dt);
        
        if(paymentToken == null || paymentToken.isEmpty()) {

            System.out.println("######## No se devolvio el PaymentToken");
        	return ;
        }
        log("executePayment", PPConnector.executePayment(pm, paymentToken, secretKey));
        
        log("getTransactions",PPConnector.getTransactions());
        
        log("getTransactionByTxComercioId",PPConnector.getTransactionByTxComercioId("097d8d7b-967b-48af-9d99-8d21666244b448"));

    }

    private static void log(String titulo, Response r) {
        System.out.println(titulo);
        String l = "code:\t\t" + r.getCode() + "\n" +
                "status:\t\t" + r.isStatus() + "\n" +
                "message:\t" + r.getMessage() + "\n" +
                "data:\t\t" + r.getData();
        System.out.println(l);
        System.out.println();
    }

}
