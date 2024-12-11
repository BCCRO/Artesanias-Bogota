package com.ud.artesanias_bogota.services;

import com.ud.artesanias_bogota.models.Factura;
import com.ud.artesanias_bogota.models.Producto;
import com.ud.artesanias_bogota.repositories.FacturaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.List;

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

    public  List<Factura> obtenerFacturas(){
      return facturaRepository.findAll();
    }

    public Boolean actualizarEstado(String id, String estado){
      try {
        System.out.println(estado);
        // Verificar si el estado es válido
        if (!estado.equals("P") && !estado.equals("C") && !estado.equals("E")) {
            throw new IllegalArgumentException("Estado inválido");
        }
        // Buscar la factura
        Factura factura = facturaRepository.findById(Long.parseLong(id))
            .orElseThrow(() -> new RuntimeException("No encontrado"));

        // Actualizar el estado
        factura.setEstado(estado);
        facturaRepository.save(factura); // Guardar los cambios
        return true;
    } catch (IllegalArgumentException e) {
        System.err.println("Error: " + e.getMessage());
    } catch (RuntimeException e) {
        if (e.getMessage().equals("No encontrado")) {
            return false;
        }
    }
    return false;
  }

}
