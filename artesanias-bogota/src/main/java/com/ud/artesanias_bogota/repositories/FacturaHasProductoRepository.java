package com.ud.artesanias_bogota.repositories;

import com.ud.artesanias_bogota.models.FacturaHasProducto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar operaciones CRUD y consultas personalizadas para la entidad {@link FacturaHasProducto}.
 * Extiende {@link JpaRepository} para proporcionar métodos estándar de persistencia.
 */
@Repository
public interface FacturaHasProductoRepository extends JpaRepository<FacturaHasProducto, Long> {

    /**
     * Verifica si existe una relación entre una factura y un producto específico.
     * 
     * @param idFactura identificador de la factura.
     * @param idProducto identificador del producto.
     * @return true si la relación existe, false en caso contrario.
     */
    boolean existsByIdFacturaAndIdProducto(Long idFactura, Long idProducto);

    /**
     * Busca una relación específica entre una factura y un producto.
     * 
     * @param idFactura identificador de la factura.
     * @param idProducto identificador del producto.
     * @return un {@link Optional} que contiene la relación si existe, o vacío en caso contrario.
     */
    Optional<FacturaHasProducto> findByIdFacturaAndIdProducto(Long idFactura, Long idProducto);
}