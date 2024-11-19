package com.ud.artesanias_bogota.services;

import com.ud.artesanias_bogota.models.Factura;
import com.ud.artesanias_bogota.models.Producto;
import com.ud.artesanias_bogota.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;

    public Factura crearFactura(String documentoUsuario){
        Factura factura = new Factura();
        factura.setFechaEmision(new Date());
        factura.setTotal(0L);
        factura.setTotalImpuesto(0L);
        factura.setTotaldescuento(0L);
        factura.setIdUsuarioDocumento(documentoUsuario);
        factura.setTransaccionId(1L);   // Quemado, la transaccion 1 hace referencia a pendiente por pago (sin transaccion)

        return facturaRepository.save(factura);
    }

    public Optional<Factura> obtenerFactura(Long idFactura){
        return facturaRepository.findById(idFactura);
    }

}
