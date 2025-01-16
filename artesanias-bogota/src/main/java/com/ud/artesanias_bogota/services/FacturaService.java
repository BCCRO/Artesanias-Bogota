package com.ud.artesanias_bogota.services;

import com.ud.artesanias_bogota.models.Factura;
import com.ud.artesanias_bogota.repositories.FacturaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.List;

/**
 * Servicio para gestionar la lógica de negocio relacionada con la entidad {@link Factura}.
 * Proporciona métodos para crear, consultar, actualizar y modificar facturas.
 */
@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    /**
     * Crea una nueva factura para un usuario específico.
     *
     * @param documentoUsuario identificador del usuario al que pertenece la factura.
     * @return la instancia de la factura creada.
     */
    public Factura crearFactura(String documentoUsuario) {
        Factura factura = new Factura();
        factura.setFechaEmision(new Date());
        factura.setTotal(0L);
        factura.setTotalImpuesto(0L);
        factura.setTotaldescuento(0L);
        factura.setIdUsuarioDocumento(documentoUsuario);
        factura.setEstado("CR"); // CR = Carrito
        // factura.setTransaccionId(1L); // Transacción pendiente por pago (comentado)

        return facturaRepository.save(factura);
    }

    /**
     * Obtiene una factura específica por su identificador.
     *
     * @param idFactura identificador único de la factura.
     * @return un {@link Optional} que contiene la factura si existe, o vacío en caso contrario.
     */
    public Optional<Factura> obtenerFactura(Long idFactura) {
        return facturaRepository.findById(idFactura);
    }

    /**
     * Recupera todas las facturas almacenadas en la base de datos.
     *
     * @return una lista de todas las facturas.
     */
    public List<Factura> obtenerFacturas() {
        return facturaRepository.findAll();
    }

    /**
     * Actualiza el estado de una factura específica.
     *
     * @param id identificador de la factura.
     * @param estado nuevo estado para la factura.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarEstado(String id, String estado) {
        try {
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

    /**
     * Actualiza los datos de una factura existente.
     *
     * @param factura instancia de la factura con los datos actualizados.
     */
    public void actualizarFactura(Factura factura) {
        facturaRepository.save(factura);
    }
}
