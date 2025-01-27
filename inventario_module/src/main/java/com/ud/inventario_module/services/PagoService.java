package com.ud.inventario_module.services;

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
import com.ud.inventario_module.models.Factura;
import com.ud.inventario_module.models.Producto;
import com.ud.inventario_module.models.Transaccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Servicio para gestionar los pagos mediante la integración con MercadoPago.
 * Proporciona funcionalidades para crear preferencias de pago, gestionar transacciones
 * y consultar el estado de pagos y facturas.
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
    private FacturaService facturaService;

    @Autowired
    private TransaccionService transaccionService;

    /**
     * Constructor que inicializa el token de acceso de MercadoPago.
     */
    public PagoService() {
        MercadoPagoConfig.setAccessToken(SECRET_TOKEN);
        System.out.println(SECRET_TOKEN);
    }

    /**
     * Crea una preferencia de pago de prueba en MercadoPago.
     *
     * @return la preferencia creada.
     * @throws MPException    si ocurre un error interno en la configuración.
     * @throws MPApiException si ocurre un error al conectarse a MercadoPago.
     */
    public Preference createTestPreference() throws MPException, MPApiException {
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id("1234")
                .title("ProdPrueba")
                .description("prueba")
                .categoryId("prueba")
                .quantity(1)
                .currencyId("COP")
                .unitPrice(new BigDecimal("1000"))
                .build();

        List<PreferenceItemRequest> items = List.of(itemRequest);

        String redirectUrl = String.format("%s%s", SERVIDOR_FRONT, REDIRECT_URL);
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(redirectUrl)
                .pending(redirectUrl)
                .failure(redirectUrl)
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .notificationUrl(SERVIDOR_BACK + WEBHOOK_PAGOS)
                .metadata(Map.of("idFactura", "987654321"))
                .build();

        PreferenceClient client = new PreferenceClient();
        return client.create(preferenceRequest);
    }

    /**
     * Crea una preferencia de pago basada en los productos de una factura.
     *
     * @param idFactura identificador de la factura.
     * @return el ID de la preferencia creada.
     * @throws Exception si ocurre un error al asociar la transacción con la factura.
     */
    public String createPreferenceByFactura(Long idFactura) throws Exception {
        List<PreferenceItemRequest> items = crearListadoProductosByFactura(idFactura);
        if (items.isEmpty()) return null;

        String redirectUrl = String.format("%s%s", SERVIDOR_FRONT, REDIRECT_URL);
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(redirectUrl)
                .pending(redirectUrl)
                .failure(redirectUrl)
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .notificationUrl(SERVIDOR_BACK + WEBHOOK_PAGOS)
                .metadata(Map.of("idFactura", idFactura.toString()))
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        Transaccion transaccion = crearTransaccion();
        transaccionService.createTransaccion(transaccion);

        Factura facturaActualizada = agregarTransaccionAFactura(transaccion.getId(), idFactura);
        facturaService.actualizarFactura(facturaActualizada);

        return preference.getId();
    }

    /**
     * Genera un listado de productos basado en una factura.
     *
     * @param idFactura identificador de la factura.
     * @return una lista de productos en formato {@link PreferenceItemRequest}.
     */
    private List<PreferenceItemRequest> crearListadoProductosByFactura(Long idFactura) {
        List<PreferenceItemRequest> items = new ArrayList<>();
        facturaService.obtenerFactura(idFactura).ifPresent(factura ->
            factura.getProductosFacturas().forEach(facturaHasProducto -> {
                Producto producto = facturaHasProducto.getProducto();
                items.add(PreferenceItemRequest.builder()
                        .id(Long.toString(producto.getId()))
                        .title(producto.getNombre())
                        .description(producto.getDescripcion())
                        .categoryId(producto.getCategoriaProducto().getNombre())
                        .quantity(facturaHasProducto.getCantidad())
                        .currencyId("COP")
                        .unitPrice(BigDecimal.valueOf(producto.getPrecioUnitario()))
                        .build());
            })
        );
        return items;
    }

    /**
     * Crea una nueva transacción con estado inicial pendiente.
     *
     * @return una instancia de {@link Transaccion}.
     */
    private Transaccion crearTransaccion() {
        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(new Date());
        transaccion.setFechaActualizacion(new Date());
        transaccion.setEstado("PE");
        transaccion.setIdMedioPago(2L);
        return transaccion;
    }

    /**
     * Asocia una transacción a una factura y actualiza su estado.
     *
     * @param transaccionId identificador de la transacción.
     * @param idFactura identificador de la factura.
     * @return la factura actualizada.
     * @throws Exception si la factura no existe.
     */
    private Factura agregarTransaccionAFactura(Long transaccionId, Long idFactura) throws Exception {
        return facturaService.obtenerFactura(idFactura).map(factura -> {
            factura.setTransaccionId(transaccionId);
            factura.setEstado("PE");
            return factura;
        }).orElseThrow(() -> new Exception("No se encontró la factura: " + idFactura));
    }

    /**
     * Consulta un pago por su ID en MercadoPago.
     *
     * @param id identificador del pago.
     * @return detalles del pago realizado.
     */
    public Payment consultarPayment(Long id) {
        PaymentClient client = new PaymentClient();
        try {
            return client.get(id);
        } catch (Exception e) {
            System.err.println("Error consultando pago: " + e.getMessage());
            return null;
        }
    }

    /**
     * Consulta el estado de la transacción asociada a una factura.
     *
     * @param id identificador de la factura.
     * @return detalles de la transacción.
     */
    public Transaccion consultarEstado(Long id) {
        Factura factura = facturaService.obtenerFactura(id).orElseThrow();
        return transaccionService.getTransaccion(factura.getTransaccionId());
    }
}
