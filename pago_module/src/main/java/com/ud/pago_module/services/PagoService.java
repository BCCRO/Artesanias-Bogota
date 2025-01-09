package com.ud.pago_module.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.net.MPResource;
import com.mercadopago.net.MPResponse;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceBackUrls;
import com.ud.pago_module.models.Factura;
import com.ud.pago_module.models.Producto;
import com.ud.pago_module.models.Transaccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PagoService {

    /**
     * TODO GIGANTE - Porque no podemos cargar las props dinamicamente?????
     * ALgunos no funcionan.....
     */
    @Value("${servidor.front}")
    private String SERVIDOR_FRONT = "http://127.0.0.1:8081";
    @Value("${servidor.redirect.endpoint}")
    private String REDIRECT_URL = "/pruebaPago/pruebaPago.html";
    @Value("${servidor.back}")
    private String SERVIDOR_BACK = "https://e3a9-191-109-68-23.ngrok-free.app";
    @Value("${servidor.webhook.pagos.endpoint}")
    private String WEBHOOK_PAGOS = "/api/pagos/webhook/pagos";

    @Value("${mercadoPago.token.secret}")
    private String SECRET_TOKEN = "APP_USR-1559532781124737-110509-afae2c6da54175fa335091864c35ca06-2079614828";


    @Autowired
    private FacturaService facturaService;
    @Autowired
    private TransaccionService transaccionService;


    public PagoService() {
        MercadoPagoConfig.setAccessToken(SECRET_TOKEN);
        System.out.println(SECRET_TOKEN);
    }

    public Preference createTestPreference() throws MPException, MPApiException {

        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                        .id("1234")
                        .title("ProdPrueba")
                        .description("prueba")
//                        .pictureUrl("http://picture.com/PS5")
                        .categoryId("prueba")
                        .quantity(1)
                        .currencyId("COP")
                        .unitPrice(new BigDecimal("1000"))
                        .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        String redirectUrl = String.format("%s%s", SERVIDOR_FRONT, REDIRECT_URL);
        PreferenceBackUrlsRequest preferenceBackUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success(redirectUrl)
                .pending(redirectUrl)
                .failure(redirectUrl)
                .build();

        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("idFactura", "987654321");
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(preferenceBackUrlsRequest)
                .notificationUrl("https://e3a9-191-109-68-23.ngrok-free.app/api/pagos/webhook/prueba")
                .metadata(metadataMap)
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);
        System.out.println(preference.getId());
        return preference;
    }

    private List<PreferenceItemRequest> crearListadoProductosByFactura(Long idFactura){
        List<PreferenceItemRequest> items = new ArrayList<>();

        Optional<Factura> factura = facturaService.obtenerFactura(idFactura);
        if(factura.isPresent()){
            factura.get().getProductosFacturas().forEach(facturaHasProducto -> {
                Producto producto = facturaHasProducto.getProducto();

                PreferenceItemRequest itemRequest =
                        PreferenceItemRequest.builder()
                                .id(Long.toString(producto.getId()))    //TODO necesitamos setear el id?
                                .title(producto.getNombre())
                                .description(producto.getDescripcion())
//                              .pictureUrl(productto.getImagen())      //TODO
                                .categoryId(producto.getCategoriaProducto().getNombre())
                                .quantity(facturaHasProducto.getCantidad())
                                .currencyId("COP")
                                .unitPrice(BigDecimal.valueOf(producto.getPrecioUnitario()))
                                .build();
                items.add(itemRequest);
            });
        }

        return items;
    }

    public String createPreferenceByFactura(Long idFactura) throws Exception {

        List<PreferenceItemRequest> items = crearListadoProductosByFactura(idFactura);

        if(items.isEmpty()) return null;

        String redirectUrl = String.format("%s%s", SERVIDOR_FRONT, REDIRECT_URL);
        PreferenceBackUrlsRequest preferenceBackUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success(redirectUrl)
                .pending(redirectUrl)
                .failure(redirectUrl)
                .build();

        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put("idFactura", idFactura.toString());

        String webhookUrl = String.format("%s%s", SERVIDOR_BACK, WEBHOOK_PAGOS);
        System.out.println("webhookUrl " + webhookUrl);
        System.out.println("metadataMap " + metadataMap);
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(preferenceBackUrlsRequest)
                .notificationUrl(webhookUrl)
                .metadata(metadataMap)
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        //Creamos una transacción
        Transaccion transaccion = crearTransaccion();
        transaccionService.createTransaccion(transaccion);

        //Relacionamos Transaccion con factura
        Factura facturaActualizada = agregarTransaccionAFactura(transaccion.getId(), idFactura);
        facturaService.actualizarFactura(facturaActualizada);

        System.out.println(String.format("Se ha creado la factura con id %s de la factura %s", preference.getId(), idFactura));   //TODO utilizar logger
        return preference.getId();
    }

    private Transaccion crearTransaccion(){
        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(new Date());
        transaccion.setFechaActualizacion(new Date());
        transaccion.setEstado("PE");

        transaccion.setIdMedioPago(2L); // QUEMADO

        return transaccion;
    }

    private Factura agregarTransaccionAFactura(Long transaccionId, Long idFactura) throws Exception {
        Optional<Factura> factura = facturaService.obtenerFactura(idFactura);

        if(factura.isPresent()){
            Factura facturaPresent = factura.get();
            facturaPresent.setTransaccionId(transaccionId);
            facturaPresent.setEstado("PE");
            return facturaPresent;
        }
        else throw new Exception("No se encontro la factura: " + idFactura);
    }


    public Payment consultarPayment(Long id){

      PaymentClient client = new PaymentClient();
      try {
        Payment pago = client.get(id);
        String statusPayment = pago.getStatus();
        Map<String, Object> metadata = pago.getMetadata();
          System.out.println("metadataMap2 " + metadata);
        if(statusPayment == null || statusPayment == null) throw new Exception("No se obtuvo la información necesaria del servidor de Mercado Pago");

        if (!metadata.containsKey("id_factura")) {
          throw new Exception("No se encontro el id de la factura");
        }
        String facturaId = (String) metadata.get("id_factura");
        System.out.println("Actualizando factura " + facturaId + " con id transaccion " + id);
        String estadoFactura = "";
        String estadoTransaccion = "";
        switch (pago.getStatus()) {
            case "approved":
                estadoFactura  = "AP";
                estadoTransaccion = "AP";
                break;
            case "pending":
                estadoFactura  = "PE";
                estadoTransaccion = "PE";
                break;
            case "rejected":
                estadoFactura  = "CA";
                estadoTransaccion = "RE";
                break;
            case "cancelled":
                estadoFactura  = "CA";
                estadoTransaccion = "CA";
                break;
            case "authorized":
                estadoFactura  = "PE";
                estadoTransaccion = "AT";
                break;
            case "refunded":
                estadoFactura  = "RF";
                estadoTransaccion = "RF";
                break;
            /**
             * No deberian ser nuestros casos en esta fase de prueba
             */
            case "in_process":
                estadoFactura  = "IP";
                estadoTransaccion = "IP";
                break;
            case "in_mediation":
                estadoFactura  = "IM";
                estadoTransaccion = "IM";
                break;
            case "charged_back":
                estadoFactura  = "CB";
                estadoTransaccion = "CB";
                break;

            default:
                estadoFactura = "PE";
                estadoTransaccion = "PE";
            break;
        }

        facturaService.actualizarEstado(facturaId, estadoFactura);
        transaccionService.updateEstadoTransaccion(Long.parseLong(facturaId), estadoTransaccion, id);
        return pago;
      } catch (Exception e) {
          System.out.println(e.getMessage());
          return null;
      }
    }

    public Transaccion consultarEstado(Long id){
      //TODO: Consultar Estado transaccion
      Factura factura = facturaService.obtenerFactura(id).orElseThrow();
      Transaccion transaccion = transaccionService.getTransaccion(factura.getTransaccionId());
      try {
        return transaccion;
      } catch (Exception e) {
        throw new RuntimeException("Sucedio un error al consultar el estado");
      }
    }


}
