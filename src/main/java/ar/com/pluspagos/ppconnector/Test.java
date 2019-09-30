package ar.com.pluspagos.ppconnector;

import java.io.IOException;
import java.util.UUID;

import ar.com.pluspagos.ppconnector.models.CajaModel;
import ar.com.pluspagos.ppconnector.models.OrderModel;
import ar.com.pluspagos.ppconnector.models.Response;
import ar.com.pluspagos.ppconnector.models.TokenModel;

public class Test {

    public static void main(String[] args) throws IOException {
        String comercio = "bf63004c-4ad1-40d2-87ad-93589ca1e3d9";
        String frase = "fanendwPigrbfrS32r0t/awagKyRZkMaTr+HuwNR588=";
        String secretKey = "JAVA_31db8b37-26a6-49bc-aead-0f61a04f8c7c";

        PPConnector.init(Ambiente.TEST, comercio, frase);


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


        /*Alta Caja*/
        String codigoCajaNueva = "ABC123";
        int sucursalComercio = 15715;
        CajaModel caja = new CajaModel();
        caja.setNombre("Caja 2");
        caja.setSucursalComercioId(15715);
        caja.setCodigo("ABC123");
        caja.setFixedAmount(false);
        Response responseAltaCaja = PPConnector.caja(caja, secretKey);

        log("Response Alta Caja", responseAltaCaja);
        //
        //
        //String idOrdenPagoNueva = "ORDEN004";
        //
        ///*Generar orden de pago*/
        OrderModel order = new OrderModel();
        order.setMontoTotal("1284");
        order.setFixedAmount(true);
        order.setIdTransaccionInterno("ORDEN001");
        order.setProductos("Poducto 1, Producto 2");
        Response responseCrearOrden = PPConnector.order(order, secretKey, codigoCajaNueva, null);
        log("Generar orden de pago", responseCrearOrden);



        /*Consultas */
        Response responseOrden = PPConnector.getOrder("ABC460");
        log("Consultar por código", responseOrden);


        //Response responseOrdenByIdCaja = PPConnector.getOrderByCajaId(58);
        //log("Consultar por id caja", responseOrdenByIdCaja);

        /*delete order*/
        //Response responseDeleteOrder = PPConnector.deleteOrder("ABC460");
        //log("Delete orden de pago", responseDeleteOrder);



        ///******* getPaymentMethods ******/
        //log("GetPaymentMethods", PPConnector.getPaymentMethods());
        //
        ///******* executePayment ******/
        //System.out.println("******* executePayment ******");
        //DatosTarjeta dt = new DatosTarjeta();
        //dt.setAnoVencimiento("20");
        //dt.setMesVencimiento("08");
        //dt.setCodigoTarjeta("123");
        //dt.setDocumentoTitular("22222222");
        //dt.setEmail("email@gmail.com");
        //dt.setFechaNacimientoTitular("12031993");
        //dt.setNumeroPuertaResumen("20");
        //dt.setNumeroTarjeta("4507990000004905");
        //dt.setTipoDocumento("DNI");
        //dt.setTitularTarjeta("JUAN PEREZ");
        //
        //PaymentModel pm = new PaymentModel();
        //pm.setAceptaHabeasData(true);
        //pm.setAceptTerminosyCondiciones(true);
        //pm.setCantidadCuotas(1);
        //pm.setIpCliente(publicIp);
        //pm.setMedioPagoId(4);
        //pm.setDatosTarjeta(dt);
        //
        //if(paymentToken == null || paymentToken.isEmpty()) {
        //
        //    System.out.println("######## No se devolvio el PaymentToken");
        //	return ;
        //}
        //log("executePayment", PPConnector.executePayment(pm, paymentToken, secretKey));
        //
        //log("getTransactions",PPConnector.getTransactions());
        //
        //log("getTransactionByTxComercioId",PPConnector.getTransactionByTxComercioId("097d8d7b-967b-48af-9d99-8d21666244b448"));

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
