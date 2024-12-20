package com.ud.artesanias_bogota.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.ud.artesanias_bogota.models.Transaccion;
import com.ud.artesanias_bogota.models.request.PaymentNotification;
import com.ud.artesanias_bogota.services.PagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    PagoService pagoService;

    

    @PostMapping("/crear-preferencia/prueba")
    public void createTestPreference() throws MPException, MPApiException {
        pagoService.createTestPreference();
    }

    @GetMapping("/crear-preferencia/by-factura/{idFactura}")
    public ResponseEntity<Preference> createLinkPago(@PathVariable Long idFactura) throws MPException, MPApiException {
        Preference preference = pagoService.createPreferenceByFactura(idFactura);

        if(preference == null) {
            ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
            problemDetail.setDetail("La facturas no tiene productos");

            return ResponseEntity.internalServerError().body(null);
        }
        return ResponseEntity.ok(preference);
    }

<<<<<<< HEAD
    @PostMapping("/webhook/prueba")
    public void pruebaWebhook(@RequestBody Object body) throws MPException, MPApiException {
        System.out.println(body);
    }

=======
    @PostMapping("/webhook")
    public ResponseEntity<?> postMethodName(@RequestBody PaymentNotification notification) {

      Long dataId = notification.getData() != null ? notification.getData().getId() : null;

        if (dataId != null) { 
          try {
            return ResponseEntity.ok(pagoService.consultarPreference(dataId).getResponse().getContent());
          } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.ok().build();
          }
        } else {
          System.err.println("El atributo data.id no está presente");
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/consultar-estado-transaccion/{facturaId}")
    public ResponseEntity<?> getEstadoPago(@PathVariable Long facturaId){
      try {
        Transaccion status = pagoService.consultarEstado(facturaId);
        return ResponseEntity.ok(status);
      } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Algo inesperado sucedio");
      }
    }
    

>>>>>>> c591ac0338a268b2a863f804f969b96270da46d7
}
