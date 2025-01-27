package com.ud.pago_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.pago_module.models.Factura;
import com.ud.pago_module.models.FacturaHasProducto;
import com.ud.pago_module.models.Producto;
import com.ud.pago_module.models.dtos.FacturaHasProductoDTO;
import com.ud.pago_module.repositories.FacturaHasProductoRepository;
import com.ud.pago_module.repositories.FacturaRepository;
import com.ud.pago_module.repositories.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestionar las relaciones entre Facturas y Productos.
 */
@Service
public class FacturaHasProductoService {

    @Autowired
    private FacturaHasProductoRepository facturaHasProductoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoHasPuntoVentaService productoHasPuntoVentaService;

    /**
     * Actualiza el total y los impuestos de una factura al agregar un producto.
     *
     * @param idFactura El ID de la factura.
     * @param idProducto El ID del producto.
     * @param cantidad La cantidad de productos añadidos.
     */
    private void actualizarTotalFactura(Long idFactura, Long idProducto, int cantidad) {
        Optional<Factura> facturaOpt = facturaRepository.findById(idFactura);
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);

        if (facturaOpt.isEmpty()) {
            throw new RuntimeException("Error actualizando la factura: " + idFactura);
        } else if (productoOpt.isEmpty()) {
            throw new RuntimeException("Error encontrando el producto: " + idProducto);
        } else {
            Factura factura = facturaOpt.get();
            Producto producto = productoOpt.get();
            Long totalAnterior = factura.getTotal();
            Long nuevoValor = totalAnterior + producto.getPrecioUnitario() * cantidad;
            factura.setTotal(nuevoValor);
            double impuestos = factura.getTotal() * 0.16;
            factura.setTotalImpuesto((long) impuestos);
        }
    }

    /**
     * Añade un producto a una factura y actualiza el inventario del punto de venta.
     *
     * @param idPuntoVenta El ID del punto de venta.
     * @param idFactura El ID de la factura.
     * @param idProducto El ID del producto.
     * @param cantidad La cantidad de productos añadidos.
     */
    public void anadirProductoFactura(Long idPuntoVenta, Long idFactura, Long idProducto, int cantidad) {
        FacturaHasProducto facturaHasProducto = new FacturaHasProducto();
        facturaHasProducto.setIdFactura(idFactura);
        facturaHasProducto.setIdProducto(idProducto);
        facturaHasProducto.setCantidad(cantidad);
        facturaHasProductoRepository.save(facturaHasProducto);

        productoHasPuntoVentaService.restarUnidadProductoPuntoVenta(idProducto, idPuntoVenta);
        actualizarTotalFactura(idFactura, idProducto, cantidad);
    }

    /**
     * Añade múltiples productos a una factura.
     * También actualiza los totales e inventarios correspondientes.
     *
     * @param listadoProductos Lista de productos a añadir.
     */
    public void anadirProductosFactura(List<FacturaHasProductoDTO> listadoProductos) {
        Iterable<FacturaHasProducto> iterable = listadoProductos.stream()
                .map(dto -> {
                    productoHasPuntoVentaService.restarUnidadProductoPuntoVenta(dto.getIdProducto(), dto.getIdPuntoVenta());
                    actualizarTotalFactura(dto.getIdFactura(), dto.getIdProducto(), dto.getCantidad());
                    return new FacturaHasProducto(dto);
                })
                .collect(Collectors.toList());
        facturaHasProductoRepository.saveAll(iterable);
    }

    /**
     * Elimina un producto de una factura.
     *
     * @param idFactura El ID de la factura.
     * @param idProducto El ID del producto.
     * @return {@code true} si el producto fue eliminado correctamente, de lo contrario {@code false}.
     */
    public boolean removeItem(Long idFactura, Long idProducto) {
        if (!facturaRepository.existsById(idFactura)) {
            throw new IllegalArgumentException("La factura no existe", new Exception("404"));
        }
        if (!facturaHasProductoRepository.existsByIdFacturaAndIdProducto(idFactura, idProducto)) {
            throw new IllegalArgumentException("El producto no corresponde a esa factura", new Exception("404"));
        }

        FacturaHasProducto factura = facturaHasProductoRepository.findByIdFacturaAndIdProducto(idFactura, idProducto)
                .orElseThrow();
        try {
            facturaHasProductoRepository.delete(factura);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}