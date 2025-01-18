package com.ud.artesanias_bogota.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.ud.artesanias_bogota.models.Transaccion;
import com.ud.artesanias_bogota.models.request.PaymentNotification;
import com.ud.artesanias_bogota.services.PagoService;

@RestController // Declara esta clase como un controlador REST para manejar solicitudes relacionadas con pagos.
@RequestMapping("/api/pagos") // Define la ruta base "/api/pagos" para las solicitudes dirigidas a este controlador.
public class PagoController {

    @Autowired // Inyecta la dependencia del servicio de pagos.
    PagoService pagoService;

    @PostMapping("/crear-preferencia/prueba") // Define un endpoint para crear una preferencia de prueba.
    public void createTestPreference() throws MPException, MPApiException {
        // Llama al servicio para crear una preferencia de pago de prueba.
        pagoService.createTestPreference();
    }

    @GetMapping("/crear-preferencia/by-factura/{idFactura}") // Define un endpoint para crear un enlace de pago a partir de una factura.
    public ResponseEntity<String> createLinkPago(@PathVariable Long idFactura) throws Exception {
        // Llama al servicio para generar una preferencia de pago basada en la factura.
        String preference = pagoService.createPreferenceByFactura(idFactura);

        if (preference == null) {
            // Devuelve un problema detallado con estado 500 si no hay productos en la factura.
            ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
            problemDetail.setDetail("La factura no tiene productos");
            return ResponseEntity.internalServerError().body(null);
        }
        // Retorna el enlace de preferencia con estado 200 (OK).
        return ResponseEntity.ok(preference);
    }

    @PostMapping("/webhook/pagos") // Define un endpoint para manejar notificaciones de pagos desde un webhook.
    public ResponseEntity<?> postMethodName(@RequestBody PaymentNotification notification) {
        // Obtiene el ID de la notificación desde el objeto recibido.
        Long dataId = notification.getData() != null ? notification.getData().getId() : null;

        if (dataId != null) {
            // Registra el ID de la notificación en la consola para seguimiento.
            System.out.println("Se recibió el evento de pago con id: " + dataId);
            try {
                // Consulta el estado del pago usando el servicio de pagos.
                pagoService.consultarPayment(dataId);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                // Maneja cualquier excepción e imprime el error en la consola.
                System.err.println(e);
                return ResponseEntity.ok().build();
            }
        } else {
            // Maneja el caso donde el atributo `data.id` no está presente en la notificación.
            System.err.println("El atributo data.id no está presente");
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/consultar-estado-transaccion/{facturaId}") // Define un endpoint para consultar el estado de una transacción por ID de factura.
    public ResponseEntity<?> getEstadoPago(@PathVariable Long facturaId) {
        try {
            // Llama al servicio para consultar el estado de la transacción.
            Transaccion status = pagoService.consultarEstado(facturaId);
            // Retorna el estado de la transacción con estado 200 (OK).
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            // Maneja errores y retorna un mensaje genérico con estado 500 (Error interno del servidor).
            return ResponseEntity.internalServerError().body("Algo inesperado sucedió");
        }
    }
}