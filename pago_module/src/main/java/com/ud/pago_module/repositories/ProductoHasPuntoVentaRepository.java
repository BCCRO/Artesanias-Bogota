package com.ud.pago_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.ProductoHasPuntoVenta;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar operaciones CRUD en la entidad {@link ProductoHasPuntoVenta}.
 * Extiende {@link JpaRepository} para proporcionar métodos básicos de persistencia y
 * métodos personalizados para consultas específicas.
 */
@Repository
public interface ProductoHasPuntoVentaRepository extends JpaRepository<ProductoHasPuntoVenta, Long> {

    /**
     * Busca una relación específica entre un producto y un punto de venta.
     *
     * @param idProducto   El identificador del producto.
     * @param idPuntoVenta El identificador del punto de venta.
     * @return Un {@link Optional} que contiene la relación si existe.
     */
    Optional<ProductoHasPuntoVenta> findProductoHasVentaByIdProductoAndIdPuntoVenta(Long idProducto, Long idPuntoVenta);

    /**
     * Busca todas las relaciones de un producto en distintos puntos de venta.
     *
     * @param idProducto El identificador del producto.
     * @return Lista de relaciones del producto con puntos de venta.
     */
    List<ProductoHasPuntoVenta> findProductoHasVentaByIdProducto(Long idProducto);

    /**
     * Busca todas las relaciones de un punto de venta con distintos productos.
     *
     * @param idPuntoVenta El identificador del punto de venta.
     * @return Lista de relaciones del punto de venta con productos.
     */
    List<ProductoHasPuntoVenta> findProductoHasVentaByIdPuntoVenta(Long idPuntoVenta);
}