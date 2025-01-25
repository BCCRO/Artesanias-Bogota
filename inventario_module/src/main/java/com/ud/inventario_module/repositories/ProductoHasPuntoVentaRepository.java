package com.ud.artesanias_bogota.repositories;

import com.ud.artesanias_bogota.models.ProductoHasPuntoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para manejar operaciones CRUD y consultas personalizadas para la entidad {@link ProductoHasPuntoVenta}.
 * Extiende {@link JpaRepository} para proporcionar métodos estándar de persistencia.
 */
@Repository
public interface ProductoHasPuntoVentaRepository extends JpaRepository<ProductoHasPuntoVenta, Long> {

    /**
     * Busca una relación específica entre un producto y un punto de venta.
     *
     * @param idProducto identificador del producto.
     * @param idPuntoVenta identificador del punto de venta.
     * @return un {@link Optional} que contiene la relación si existe, o vacío en caso contrario.
     */
    Optional<ProductoHasPuntoVenta> findProductoHasVentaByIdProductoAndIdPuntoVenta(Long idProducto, Long idPuntoVenta);

    /**
     * Busca todas las relaciones de un producto con distintos puntos de venta.
     *
     * @param idProducto identificador del producto.
     * @return una lista de {@link ProductoHasPuntoVenta} asociadas al producto.
     */
    List<ProductoHasPuntoVenta> findProductoHasVentaByIdProducto(Long idProducto);

    /**
     * Busca todas las relaciones de un punto de venta con distintos productos.
     *
     * @param idPuntoVenta identificador del punto de venta.
     * @return una lista de {@link ProductoHasPuntoVenta} asociadas al punto de venta.
     */
    List<ProductoHasPuntoVenta> findProductoHasVentaByIdPuntoVenta(Long idPuntoVenta);
}