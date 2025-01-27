package com.ud.pago_module.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ud.pago_module.models.FacturaHasProducto;

/**
 * Repositorio para gestionar operaciones CRUD en la entidad {@link FacturaHasProducto}.
 * Extiende {@link JpaRepository} para proporcionar métodos básicos de persistencia y
 * métodos personalizados para consultas específicas.
 */
@Repository
public interface FacturaHasProductoRepository extends JpaRepository<FacturaHasProducto, Long> {

    /**
     * Verifica si existe una relación entre una factura y un producto específico.
     *
     * @param idFactura El identificador de la factura.
     * @param idProducto El identificador del producto.
     * @return {@code true} si la relación existe, de lo contrario {@code false}.
     */
    boolean existsByIdFacturaAndIdProducto(Long idFactura, Long idProducto);

    /**
     * Busca una relación específica entre una factura y un producto.
     *
     * @param idFactura El identificador de la factura.
     * @param idProducto El identificador del producto.
     * @return Un {@link Optional} que contiene la relación si existe.
     */
    Optional<FacturaHasProducto> findByIdFacturaAndIdProducto(Long idFactura, Long idProducto);
}