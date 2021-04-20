package ar.com.pluspagos.ppconnector.security;

import ar.com.pluspagos.ppconnector.models.CajaModel;
import ar.com.pluspagos.ppconnector.models.DatosTarjeta;
import ar.com.pluspagos.ppconnector.models.OrderModel;
import ar.com.pluspagos.ppconnector.models.PaymentModel;
import ar.com.pluspagos.ppconnector.models.TokenModel;

import com.google.gson.Gson;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Package {

    public static String getPackage(Object body, String phrase) {
        String result =  getPackage(body, phrase, null, false);
        System.out.println("GETPACKAGE: " + result);
        return result;
    }

    public static String getPackage(Object body, String phrase, String token, boolean optEncript) {
        try {
            String hash = hashString(body);
//            System.out.println(" **hashString(body): " +hash);
            setProperty(body,"hash",hash);

//            System.out.println(" **optEncript: " +optEncript);
            if (optEncript) {
                encryptPaymentData(body, token);
            }
            Gson gson = new Gson();
            String bodyString = gson.toJson(body);
//            System.out.println("*GETPACKAGE Sin encriptar: " + bodyString);
//            System.out.println("*phrase: " + phrase);
            return encryptAES256(bodyString, phrase);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String hashString(Object model) {
        return hashSHA256(model);
    }

    private static String encryptAES256(String body, String phrase) {
        return encryptString(body, phrase);
    }

    private static Object encryptPaymentData(Object body, String token) {
        return encryptData(body, token);
    }

    private static Object encryptData(Object body, String token) {
        String encryptKey = token.substring(12, 20);

        try {
            for(Field property:body.getClass().getDeclaredFields()){
                Object propertyValue = getProperty(body, property.getName());
                if (propertyValue != null) {
                    if (property.getType().equals(DatosTarjeta.class)) {
                        for (Field sonProperty : propertyValue.getClass().getDeclaredFields()) {
                            if (!sonProperty.getName().equals("email")) {
                                //TODO como verr si se puede escribir
//                                if (PropertyUtils.isWriteable(propertyValue, sonProperty.getName())) {
                                    Object value = getProperty(propertyValue, sonProperty.getName());
                                    if (value != null) {
                                        setProperty(propertyValue, sonProperty.getName(), encryptString(value.toString(), encryptKey));
                                    }
//                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

    private static String hashSHA256(Object model) {
        try {
            String input = "";
            Map<String,String> map = new HashMap<String, String>();

            for (Field propertyInfo:model.getClass().getDeclaredFields()) {
                if (propertyInfo.getName().equals("class")) continue;

//                System.out.println("***model.getClass(): " + model.getClass());
                Object propertyValue = getProperty(model, propertyInfo.getName());
                String value= "";
                if (propertyValue != null) {
//                    System.out.println("**Properti Name: " + propertyInfo.getName());
                    if (!propertyInfo.getType().isArray() && !propertyInfo.getType().equals(DatosTarjeta.class)) {
                        input += String.format("%s*", propertyValue.toString());
                        value = String.format("%s*", propertyValue.toString());
                    } else if (propertyInfo.getType().equals(DatosTarjeta.class)) {
                        Map<String,String> mapSon = new HashMap<String, String>();
                        String valueSon = "";
                        for (Field sonProperty : propertyValue.getClass().getDeclaredFields()) {
//                        	System.out.println("**Properti Name Son: " + sonProperty.getName());
                            input += String.format("%s*", getProperty(propertyValue, sonProperty.getName()).toString());
                            valueSon =String.format("%s*", getProperty(propertyValue, sonProperty.getName()).toString());
                            mapSon.put(sonProperty.getName(), valueSon);
                        }
                        List<String> campos = Arrays.asList("numeroTarjeta","titularTarjeta","codigoTarjeta"
                    			,"anoVencimiento","mesVencimiento","tipoDocumento","documentoTitular"
                    			,"fechaNacimientoTitular","numeroPuertaResumen","email");
                        value = getStringOrdenado(mapSon,campos);

                    } else if (propertyInfo.getType().isArray()) {
                        for (int i = 0; i < Array.getLength(propertyValue); i++) {
                            Object o = Array.get(propertyValue, i);
                            input += String.format("%s*", o.toString());
                            value += String.format("%s*", o.toString());
                        }
                    }
//                    System.out.println("**Properti Name: " + value);
                    map.put(propertyInfo.getName(), value);
                }
            }

//            System.out.println("String concatenado Desordenado: " + input);
            //TODO: ordenamos los campos paa quequeden igual que c#
            if(model.getClass().equals(TokenModel.class)){
            	System.out.println("Orden TokenModel.class ");
            	List<String> campos = Arrays.asList("comercio", "urlDominio", "productos","totalOperacion","sucursalComercio","transaccionComercioId","hash","ip");
            	input= getStringOrdenado(map,campos);
            }else if(model.getClass().equals(PaymentModel.class)) {
            	System.out.println("Orden PaymentModel.class ");
            	input= getStringOrdenadoPaymentModel(map);
            }else if(model.getClass().equals(CajaModel.class)){
                System.out.println("Caja CajaModel.class");
                List<String> campos = Arrays.asList("nombre", "codigo", "sucursalComercioId", "fixedAmount");
                input = getStringOrdenado(map, campos);
            }else if(model.getClass().equals(OrderModel.class)){
                System.out.printf("Order OrderModel.class");
                List<String> campos = Arrays.asList("idTransaccionInterno", "urlNotificacion", "montoTotal", "productos");
                input = getStringOrdenado(map, campos);
            }
            
            input = input.substring(0, input.length() - 1);
            System.out.println("String concatenado: " + input);
            byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
            MessageDigest provider = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = provider.digest(inputBytes);
            StringBuilder output = new StringBuilder(2 * hashedBytes.length);
            for (byte b : hashedBytes) {
                output.append(String.format("%02X", b).toLowerCase());
            }
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private static String getStringOrdenado(Map<String, String> map,List<String> camposOrdenados) {
    	String s = "";
    	String p;
    	for (String campo : camposOrdenados) {
    		p = map.get(campo);
    		if(p != null && (p.equals("true*") || p.equals("false*"))){
    		    p = ucFirst(p);
            }
        	s += p == null ? "": p ;
		}
    	return s;
    }
    

    private static String getStringOrdenadoPaymentModel(Map<String, String> map) {
    	//List<String> campos = Arrays.asList("datosTarjeta","medioPagoId","cantidadCuotas","aceptaHabeasData","aceptTerminosyCondiciones","ipCliente","hash");
    	//TODO: hay que pasar la primera en mayuscula de los boolean 
    	String s = "";
    	String p;
    	p = map.get("datosTarjeta");
    	s += p == null ? "": p ;
    	
    	p = map.get("medioPagoId");
    	s += p == null ? "": p ;
    	
    	p = map.get("cantidadCuotas");
    	s += p == null ? "": p ;
    	
    	p = map.get("aceptaHabeasData");
    	s += p == null ? "": ucFirst(p) ;
    	
    	p = map.get("aceptTerminosyCondiciones");
    	s += p == null ? "": ucFirst(p) ;
    	
    	p = map.get("ipCliente");
    	s += p == null ? "": p ;
    	
    	p = map.get("hash");
    	s += p == null ? "": p ;
    	
    	return s;
    }
    public static String ucFirst(String str) {
		  if (str == null || str.isEmpty()) {
		    return str;            
		  } else {
		    return str.substring(0, 1).toUpperCase() + str.substring(1); 
		  }
	}
    
    public static String encryptString(String plainText, String phrase) {
        try {
            while (phrase.length() < 32) {
                phrase = phrase + phrase;
            }
            phrase = phrase.substring(0, 32);

            byte[] ba = phrase.getBytes("utf-8");

            Security.setProperty("crypto.policy", "unlimited");

            SecretKey key = new SecretKeySpec(ba, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] iv = cipher.getIV();

            byte[] encrypted = cipher.doFinal(plainText.getBytes("utf-8"));

            byte[] combinedIvCt = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combinedIvCt, 0, iv.length);
            System.arraycopy(encrypted, 0, combinedIvCt, iv.length, encrypted.length);

            System.out.println("Encriptado del body: " + Base64.getEncoder().encodeToString(combinedIvCt) + " con la key " + phrase + ".");

            return Base64.getEncoder().encodeToString(combinedIvCt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean setProperty(Object bean, String fieldName, Object fieldValue) {
        Class<?> clazz = bean.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(bean, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static <V> V getProperty(Object bean, String fieldName) {
        Class<?> clazz = bean.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (V) field.get(bean);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }

}
