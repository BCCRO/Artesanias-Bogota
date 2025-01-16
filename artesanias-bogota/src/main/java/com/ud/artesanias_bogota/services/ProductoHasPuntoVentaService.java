package com.ud.artesanias_bogota.services;

import com.ud.artesanias_bogota.models.ProductoHasPuntoVenta;
import com.ud.artesanias_bogota.repositories.ProductoHasPuntoVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar la relación entre productos y puntos de venta.
 * Permite actualizar inventarios, reducir unidades de productos y consultar
 * información sobre productos en puntos de venta específicos.
 */
@Service
public class ProductoHasPuntoVentaService {

    @Autowired
    private ProductoHasPuntoVentaRepository productoHasPuntoVentaRepository;

    /**
     * Actualiza el inventario de un producto en un punto de venta específico.
     *
     * @param idProducto   identificador del producto.
     * @param idPuntoVenta identificador del punto de venta.
     * @param cantidad     nueva cantidad para el producto en ese punto de venta.
     * @throws RuntimeException si no se encuentra el producto en el punto de venta.
     */
    public void actualizarInventario(Long idProducto, Long idPuntoVenta, int cantidad) {
        Optional<ProductoHasPuntoVenta> productoHasPuntoVentaResponse =
                productoHasPuntoVentaRepository.findProductoHasVentaByIdProductoAndIdPuntoVenta(idProducto, idPuntoVenta);

        if (productoHasPuntoVentaResponse.isPresent()) {
            ProductoHasPuntoVenta productoHasPuntoVenta = productoHasPuntoVentaResponse.get();
            productoHasPuntoVenta.setCantidad(cantidad);
            productoHasPuntoVentaRepository.save(productoHasPuntoVenta);
        } else {
            throw new RuntimeException(String.format("No se encontró el producto: %s para el punto de venta: %s", idProducto, idPuntoVenta));
        }
    }

    /**
     * Reduce en una unidad la cantidad de un producto en un punto de venta.
     *
     * @param idProducto   identificador del producto.
     * @param idPuntoVenta identificador del punto de venta.
     * @throws RuntimeException si no se encuentra el producto o si la cantidad es 0.
     */
    public void restarUnidadProductoPuntoVenta(Long idProducto, Long idPuntoVenta) {
        Optional<ProductoHasPuntoVenta> productoHasPuntoVentaResponse =
                productoHasPuntoVentaRepository.findProductoHasVentaByIdProductoAndIdPuntoVenta(idProducto, idPuntoVenta);

        if (productoHasPuntoVentaResponse.isPresent()
                && productoHasPuntoVentaResponse.get().getCantidad() > 0) {
            ProductoHasPuntoVenta productoHasPuntoVenta = productoHasPuntoVentaResponse.get();
            int nuevaCantidad = productoHasPuntoVenta.getCantidad() - 1;
            productoHasPuntoVenta.setCantidad(nuevaCantidad);
            productoHasPuntoVentaRepository.save(productoHasPuntoVenta);
        } else {
            System.out.println(String.format("No se encontró el producto: %s en el punto de venta: %s", idProducto, idPuntoVenta));
        }
    }

    /**
     * Obtiene todos los productos registrados en los puntos de venta.
     *
     * @return lista de relaciones entre productos y puntos de venta.
     */
    public List<ProductoHasPuntoVenta> getProductosHasPuntoVenta() {
        return productoHasPuntoVentaRepository.findAll();
    }

    /**
     * Obtiene todos los productos registrados en un punto de venta específico.
     *
     * @param idPuntoVenta identificador del punto de venta.
     * @return lista de productos en el punto de venta.
     */
    public List<ProductoHasPuntoVenta> getProductosHasPuntoVentaByIdPuntoVenta(Long idPuntoVenta) {
        return productoHasPuntoVentaRepository.findProductoHasVentaByIdPuntoVenta(idPuntoVenta);
    }

    /**
     * Obtiene todos los puntos de venta donde se encuentra un producto específico.
     *
     * @param idProducto identificador del producto.
     * @return lista de puntos de venta donde se encuentra el producto.
     */
    public List<ProductoHasPuntoVenta> getProductosHasPuntoVentaByIdProducto(Long idProducto) {
        return productoHasPuntoVentaRepository.findProductoHasVentaByIdProducto(idProducto);
    }
}
