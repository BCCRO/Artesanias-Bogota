package com.ud.artesanias_bogota.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
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

    @PostMapping("/webhook/prueba")
    public void pruebaWebhook(@RequestBody Object body) throws MPException, MPApiException {
        System.out.println(body);
    }

}
