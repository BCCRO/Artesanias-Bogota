package com.ud.pago_module.services;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.ud.pago_module.models.Factura;
import com.ud.pago_module.models.Producto;
import com.ud.pago_module.models.Transaccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Servicio para la gestión de pagos y facturas utilizando la integración con Mercado Pago.
 */
@Service
public class PagoService {

    @Value("${servidor.front}")
    private String SERVIDOR_FRONT;
    @Value("${servidor.redirect.endpoint}")
    private String REDIRECT_URL;
    @Value("${servidor.back}")
    private String SERVIDOR_BACK;
    @Value("${servidor.webhook.pagos.endpoint}")
    private String WEBHOOK_PAGOS;

    @Value("${mercadoPago.token.secret}")
    private String SECRET_TOKEN;

    @Autowired
    private FacturaAsyncService facturaAsyncService;

    @Autowired
    private FacturaService facturaService;
    @Autowired
    private TransaccionService transaccionService;

    /**
     * Constructor que inicializa la configuración de Mercado Pago.
     */
    public PagoService() {
        MercadoPagoConfig.setAccessToken(SECRET_TOKEN);
        System.out.println(SECRET_TOKEN);
    }

    /**
     * Crea una preferencia de pago de prueba con datos estáticos.
     *
     * @return Objeto Preference que contiene los detalles de la preferencia creada.
     * @throws MPException   Si ocurre un error al interactuar con la API de Mercado Pago.
     * @throws MPApiException Si ocurre un error de la API.
     */
    public Preference createTestPreference() throws MPException, MPApiException {
        // Configura un producto de prueba
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id("1234")
                .title("ProdPrueba")
                .description("prueba")
                .categoryId("prueba")
                .quantity(1)
                .currencyId("COP")
                .unitPrice(new BigDecimal("1000"))
                .build();
        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

        // Configura las URLs de redirección
        String redirectUrl = String.format("%s%s", SERVIDOR_FRONT, REDIRECT_URL);
        PreferenceBackUrlsRequest preferenceBackUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success(redirectUrl)
                .pending(redirectUrl)
                .failure(redirectUrl)
                .build();

        // Crea la preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(preferenceBackUrlsRequest)
                .notificationUrl("https://e3a9-191-109-68-23.ngrok-free.app/api/pagos/webhook/prueba")
                .build();

        PreferenceClient client = new PreferenceClient();
        return client.create(preferenceRequest);
    }

    /**
     * Crea un listado de productos asociados a una factura específica.
     *
     * @param idFactura Identificador de la factura.
     * @return Lista de objetos PreferenceItemRequest con los detalles de los productos.
     */
    private List<PreferenceItemRequest> crearListadoProductosByFactura(Long idFactura) {
        List<PreferenceItemRequest> items = new ArrayList<>();
        Optional<Factura> factura = facturaService.obtenerFactura(idFactura);

        if (factura.isPresent()) {
            factura.get().getProductosFacturas().forEach(facturaHasProducto -> {
                Producto producto = facturaHasProducto.getProducto();
                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(Long.toString(producto.getId()))
                        .title(producto.getNombre())
                        .description(producto.getDescripcion())
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

    /**
     * Crea una preferencia de pago para una factura específica.
     *
     * @param idFactura Identificador de la factura.
     * @return El ID de la preferencia creada.
     * @throws Exception Si ocurre un error al procesar la factura.
     */
    public String createPreferenceByFactura(Long idFactura) throws Exception {
        List<PreferenceItemRequest> items = crearListadoProductosByFactura(idFactura);
        if (items.isEmpty()) return null;

        String redirectUrl = String.format("%s%s", SERVIDOR_FRONT, REDIRECT_URL);
        PreferenceBackUrlsRequest preferenceBackUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success(redirectUrl)
                .pending(redirectUrl)
                .failure(redirectUrl)
                .build();

        String webhookUrl = String.format("%s%s", SERVIDOR_BACK, WEBHOOK_PAGOS);
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(preferenceBackUrlsRequest)
                .notificationUrl(webhookUrl)
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        Transaccion transaccion = crearTransaccion();
        transaccionService.createTransaccion(transaccion);
        Factura facturaActualizada = agregarTransaccionAFactura(transaccion.getId(), idFactura);
        facturaService.actualizarFactura(facturaActualizada);

        //Cambiamos de estado automaticamente
        facturaAsyncService.updateStateAutomatically(facturaActualizada, "CO", 180000L);

        return preference.getId();
    }

    /**
     * Crea una nueva transacción con estado inicial.
     *
     * @return Objeto Transaccion con los detalles de la transacción.
     */
    private Transaccion crearTransaccion() {
        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(new Date());
        transaccion.setFechaActualizacion(new Date());
        transaccion.setEstado("PE");
        transaccion.setIdMedioPago(2L); // ID del medio de pago (quemado)
        return transaccion;
    }

    /**
     * Asocia una transacción a una factura existente y actualiza su estado.
     *
     * @param transaccionId Identificador de la transacción.
     * @param idFactura     Identificador de la factura.
     * @return La factura actualizada.
     * @throws Exception Si la factura no es encontrada.
     */
    private Factura agregarTransaccionAFactura(Long transaccionId, Long idFactura) throws Exception {
        Optional<Factura> factura = facturaService.obtenerFactura(idFactura);
        if (factura.isPresent()) {
            Factura facturaPresent = factura.get();
            facturaPresent.setTransaccionId(transaccionId);
            facturaPresent.setEstado("PE");
            return facturaPresent;
        } else {
            throw new Exception("No se encontró la factura: " + idFactura);
        }
    }

    /**
     * Consulta el estado de un pago en Mercado Pago.
     *
     * @param id Identificador del pago en Mercado Pago.
     * @return Objeto Payment con los detalles del pago.
     */
    public Payment consultarPayment(Long id) {
        PaymentClient client = new PaymentClient();
        try {
            return client.get(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Consulta el estado de la transacción asociada a una factura.
     *
     * @param id Identificador de la factura.
     * @return Objeto Transaccion con los detalles de la transacción.
     * @throws Exception Si la factura no es encontrada.
     */
    public Transaccion consultarEstado(Long id) throws Exception {
        Factura factura = facturaService.obtenerFactura(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
        return transaccionService.getTransaccion(factura.getTransaccionId());
    }
}