package com.ud.pago_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.pago_module.models.ProductoHasPuntoVenta;
import com.ud.pago_module.repositories.ProductoHasPuntoVentaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar la relación entre productos y puntos de venta.
 */
@Service
public class ProductoHasPuntoVentaService {

    @Autowired
    private ProductoHasPuntoVentaRepository productoHasPuntoVentaRepository;

    /**
     * Actualiza el inventario de un producto en un punto de venta específico.
     *
     * @param idProducto   Identificador del producto.
     * @param idPuntoVenta Identificador del punto de venta.
     * @param cantidad     Nueva cantidad del producto en el inventario.
     * @throws RuntimeException Si no se encuentra la relación entre el producto y el punto de venta.
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
     * Resta una unidad del inventario de un producto en un punto de venta.
     *
     * @param idProducto   Identificador del producto.
     * @param idPuntoVenta Identificador del punto de venta.
     * @throws RuntimeException Si no se encuentra la relación entre el producto y el punto de venta.
     */
    public void restarUnidadProductoPuntoVenta(Long idProducto, Long idPuntoVenta) {
        Optional<ProductoHasPuntoVenta> productoHasPuntoVentaResponse = 
            productoHasPuntoVentaRepository.findProductoHasVentaByIdProductoAndIdPuntoVenta(idProducto, idPuntoVenta);

        if (productoHasPuntoVentaResponse.isPresent()) {
            ProductoHasPuntoVenta productoHasPuntoVenta = productoHasPuntoVentaResponse.get();
            int cantidadActual = productoHasPuntoVenta.getCantidad();
            int nuevaCantidad = cantidadActual - 1;
            productoHasPuntoVenta.setCantidad(nuevaCantidad);
            productoHasPuntoVentaRepository.save(productoHasPuntoVenta);
        } else {
            throw new RuntimeException(String.format("No se encontró el producto: %s para el punto de venta: %s", idProducto, idPuntoVenta));
        }
    }

    /**
     * Obtiene la lista de todas las relaciones entre productos y puntos de venta.
     *
     * @return Lista de objetos {@link ProductoHasPuntoVenta}.
     */
    public List<ProductoHasPuntoVenta> getProductosHasPuntoVenta() {
        return productoHasPuntoVentaRepository.findAll();
    }

    /**
     * Obtiene la lista de productos relacionados con un punto de venta específico.
     *
     * @param idPuntoVenta Identificador del punto de venta.
     * @return Lista de objetos {@link ProductoHasPuntoVenta}.
     */
    public List<ProductoHasPuntoVenta> getProductosHasPuntoVentaByIdPuntoVenta(Long idPuntoVenta) {
        return productoHasPuntoVentaRepository.findProductoHasVentaByIdPuntoVenta(idPuntoVenta);
    }

    /**
     * Obtiene la lista de puntos de venta relacionados con un producto específico.
     *
     * @param idProducto Identificador del producto.
     * @return Lista de objetos {@link ProductoHasPuntoVenta}.
     */
    public List<ProductoHasPuntoVenta> getProductosHasPuntoVentaByIdProducto(Long idProducto) {
        return productoHasPuntoVentaRepository.findProductoHasVentaByIdProducto(idProducto);
    }
}