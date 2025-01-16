package com.ud.artesanias_bogota.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.ud.artesanias_bogota.models.Transaccion;
import com.ud.artesanias_bogota.models.request.PaymentNotification;
import com.ud.artesanias_bogota.services.PagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de pagos.
 * Integra funcionalidades de MercadoPago para crear preferencias de pago,
 * gestionar notificaciones de pagos (webhook) y consultar el estado de transacciones.
 */
@RestController // Indica que esta clase es un controlador REST.
@RequestMapping("/api/pagos") // Define el prefijo de las rutas para los endpoints relacionados con pagos.
public class PagoController {

    @Autowired // Inyección del servicio de pagos.
    private PagoService pagoService;

    /**
     * Crea una preferencia de pago de prueba en MercadoPago.
     * 
     * @throws MPException si ocurre un error en la configuración de MercadoPago.
     * @throws MPApiException si ocurre un error con la API de MercadoPago.
     */
    @PostMapping("/crear-preferencia/prueba")
    public void createTestPreference() throws MPException, MPApiException {
        pagoService.createTestPreference();
    }

    /**
     * Crea un enlace de pago para una factura específica.
     * 
     * @param idFactura ID de la factura para la cual se generará el enlace de pago.
     * @return el enlace de pago generado o un estado HTTP 500 si la factura no tiene productos.
     * @throws Exception si ocurre un error al generar la preferencia de pago.
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
     * Gestiona las notificaciones de pago enviadas por MercadoPago (webhook).
     * 
     * @param notification objeto que contiene los detalles de la notificación de pago.
     * @return un estado HTTP 200 si se procesa correctamente, incluso si no se encuentra el atributo data.id.
     */
    @PostMapping("/webhook/pagos")
    public ResponseEntity<?> postMethodName(@RequestBody PaymentNotification notification) {
        Long dataId = notification.getData() != null ? notification.getData().getId() : null;

        if (dataId != null) {
            System.out.println("Se recibió el evento de pago con ID: " + dataId);
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
     * Consulta el estado de pago de una factura específica.
     * 
     * @param facturaId ID de la factura a consultar.
     * @return el estado de la transacción asociada a la factura o un estado HTTP 500 en caso de error.
     */
    @GetMapping("/consultar-estado-transaccion/{facturaId}")
    public ResponseEntity<?> getEstadoPago(@PathVariable Long facturaId) {
        try {
            Transaccion status = pagoService.consultarEstado(facturaId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Algo inesperado sucedió");
        }
    }
}

