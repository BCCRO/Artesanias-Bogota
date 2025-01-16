package com.ud.login_module.controllers;

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
import com.ud.login_module.models.Transaccion;
import com.ud.login_module.models.request.PaymentNotification;
import com.ud.login_module.services.PagoService;


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
    public ResponseEntity<String> createLinkPago(@PathVariable Long idFactura) throws Exception {
        String preference = pagoService.createPreferenceByFactura(idFactura);

        if(preference == null) {
            ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
            problemDetail.setDetail("La facturas no tiene productos");

            return ResponseEntity.internalServerError().body(null);
        }
        return ResponseEntity.ok(preference);
    }

    @PostMapping("/webhook/pagos")
    public ResponseEntity<?> postMethodName(@RequestBody PaymentNotification notification) {

        Long dataId = notification.getData() != null ? notification.getData().getId() : null;

        if (dataId != null) {
            System.out.println("Se recibio el evento de pago con id: " + dataId); //TODO pasar a logger
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

    @GetMapping("/consultar-estado-transaccion/{facturaId}")
    public ResponseEntity<?> getEstadoPago(@PathVariable Long facturaId){
      try {
        Transaccion status = pagoService.consultarEstado(facturaId);
        return ResponseEntity.ok(status);
      } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Algo inesperado sucedio");
      }
    }
    

}
