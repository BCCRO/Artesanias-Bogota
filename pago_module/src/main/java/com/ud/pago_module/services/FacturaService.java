package com.ud.pago_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.pago_module.models.Factura;
import com.ud.pago_module.repositories.FacturaRepository;

import java.util.Date;
import java.util.Optional;
import java.util.List;

/**
 * Servicio para gestionar las operaciones relacionadas con la entidad {@link Factura}.
 */
@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    /**
     * Crea una nueva factura asociada a un usuario específico.
     *
     * @param documentoUsuario El documento del usuario asociado a la factura.
     * @return La factura creada.
     */
    public Factura crearFactura(String documentoUsuario) {
        Factura factura = new Factura();
        factura.setFechaEmision(new Date());
        factura.setTotal(0L);
        factura.setTotalImpuesto(0L);
        factura.setTotaldescuento(0L);
        factura.setIdUsuarioDocumento(documentoUsuario);
        factura.setEstado("CR"); // Estado inicial: CR (Carrito)
        return facturaRepository.save(factura);
    }

    /**
     * Obtiene una factura por su ID.
     *
     * @param idFactura El ID de la factura.
     * @return Un {@link Optional} que contiene la factura si existe, de lo contrario está vacío.
     */
    public Optional<Factura> obtenerFactura(Long idFactura) {
        return facturaRepository.findById(idFactura);
    }

    /**
     * Obtiene una lista de todas las facturas almacenadas.
     *
     * @return Lista de facturas.
     */
    public List<Factura> obtenerFacturas() {
        return facturaRepository.findAll();
    }

    /**
     * Actualiza el estado de una factura específica.
     *
     * @param id     El ID de la factura.
     * @param estado El nuevo estado de la factura.
     * @return {@code true} si el estado se actualizó correctamente, de lo contrario {@code false}.
     */
    public boolean actualizarEstado(String id, String estado) {
        try {
            // Buscar la factura por ID
            Factura factura = facturaRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new RuntimeException("No encontrado"));

            // Actualizar el estado
            factura.setEstado(estado);
            facturaRepository.save(factura); // Guardar los cambios
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (RuntimeException e) {
            if ("No encontrado".equals(e.getMessage())) {
                return false;
            }
        }
        return false;
    }

    /**
     * Actualiza una factura específica con nuevos datos.
     *
     * @param factura La factura con los datos actualizados.
     */
    public void actualizarFactura(Factura factura) {
        facturaRepository.save(factura);
    }
}