package com.ud.pago_module.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.ud.pago_module.models.Transaccion;
import com.ud.pago_module.models.request.PaymentNotification;
import com.ud.pago_module.services.PagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de pagos.
 * Proporciona endpoints para la creación de preferencias de pago, notificaciones de webhook y consulta de transacciones.
 */
@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    PagoService pagoService;

    /**
     * Crea una preferencia de prueba en MercadoPago.
     *
     * @throws MPException    Si ocurre un error relacionado con MercadoPago.
     * @throws MPApiException Si ocurre un error en la API de MercadoPago.
     */
    @PostMapping("/crear-preferencia/prueba")
    public void createTestPreference() throws MPException, MPApiException {
        pagoService.createTestPreference();
    }

    /**
     * Crea un enlace de pago para una factura específica.
     *
     * @param idFactura El ID de la factura para la que se creará el enlace de pago.
     * @return El enlace de pago o un error si la factura no tiene productos.
     * @throws Exception Si ocurre un error al crear la preferencia.
     */
    @GetMapping("/crear-preferencia/by-factura/{idFactura}")
    public ResponseEntity<String> createLinkPago(@PathVariable Long idFactura) throws Exception {
        String preference = pagoService.createPreferenceByFactura(idFactura);

        if (preference == null) {
            ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
            problemDetail.setDetail("La factura no tiene productos");

            return ResponseEntity.internalServerError().body(null);
        }
        return ResponseEntity.ok(preference);
    }

    /**
     * Endpoint para recibir notificaciones de pago desde un webhook.
     *
     * @param notification La notificación recibida con los datos del pago.
     * @return Respuesta indicando el resultado del procesamiento.
     */
    @PostMapping("/webhook/pagos")
    public ResponseEntity<?> handlePaymentNotification(@RequestBody PaymentNotification notification) {
        Long dataId = notification.getData() != null ? notification.getData().getId() : null;

        if (dataId != null) {
            System.out.println("Se recibió el evento de pago con id: " + dataId);
            try {
                pagoService.consultarPayment(dataId);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                System.err.println(e);
                return ResponseEntity.ok().build();
            }
        } else {
            System.err.println("El atributo data.id no está presente");
            return ResponseEntity.ok().build();
        }
    }

    /**
     * Consulta el estado de una transacción basada en el ID de la factura.
     *
     * @param facturaId El ID de la factura para la que se consulta el estado.
     * @return El estado de la transacción o un error si no se encuentra.
     */
    @GetMapping("/consultar-estado-transaccion/{facturaId}")
    public ResponseEntity<?> getEstadoPago(@PathVariable Long facturaId) {
        try {
            Transaccion status = pagoService.consultarEstado(facturaId);
            return ResponseEntity.ok(status);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Algo inesperado sucedió");
        }
    }
}