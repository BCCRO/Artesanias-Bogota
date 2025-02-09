package com.ud.pago_module.services;

import com.ud.pago_module.models.Factura;
import com.ud.pago_module.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class FacturaAsyncService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Async
    public void updateStateAutomatically(Factura factura, String estado, Long timeSleep) {

        try {
            Thread.sleep(timeSleep);
            factura.setEstado(estado);

            System.out.println("Actualizando el estado de la factura: " + factura.getId());
            facturaRepository.save(factura);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
