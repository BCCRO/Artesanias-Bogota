package com.ud.artesanias_bogota.controllers;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.ud.artesanias_bogota.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    PagoService pagoService;

    @PostMapping("/crear-preferencia/prueba")
    public void createTestPreference() throws MPException, MPApiException {
        pagoService.createPreference();
    }

}
