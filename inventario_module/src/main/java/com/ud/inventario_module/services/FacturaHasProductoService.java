package com.ud.inventario_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.inventario_module.models.Factura;
import com.ud.inventario_module.models.FacturaHasProducto;
import com.ud.inventario_module.models.Producto;
import com.ud.inventario_module.models.dtos.FacturaHasProductoDTO;
import com.ud.inventario_module.repositories.FacturaHasProductoRepository;
import com.ud.inventario_module.repositories.FacturaRepository;
import com.ud.inventario_module.repositories.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para manejar la lógica de negocio relacionada con las relaciones entre facturas y productos.
 * Proporciona métodos para agregar, actualizar y eliminar productos en una factura.
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
     * Actualiza el total de una factura al agregar o modificar un producto.
     *
     * @param idFactura identificador de la factura.
     * @param idProducto identificador del producto.
     * @param cantidad cantidad del producto.
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
            Long nuevoValor = (totalAnterior + producto.getPrecioUnitario()) * cantidad;
            factura.setTotal(nuevoValor);
            double impuestos = factura.getTotal() * 0.16;
            factura.setTotalImpuesto((long) impuestos);
        }
    }

    /**
     * Agrega un producto a una factura y actualiza los totales.
     *
     * @param idPuntoVenta identificador del punto de venta.
     * @param idFactura identificador de la factura.
     * @param idProducto identificador del producto.
     * @param cantidad cantidad del producto.
     */
    public void anadirProductoFactura(Long idPuntoVenta, Long idFactura, Long idProducto, int cantidad) {
        FacturaHasProducto facturaHasProducto = new FacturaHasProducto();
        facturaHasProducto.setIdFactura(idFactura);
        facturaHasProducto.setIdProducto(idProducto);
        facturaHasProducto.setCantidad(cantidad);
        facturaHasProducto.setIdPuntoVenta(idPuntoVenta);
        facturaHasProductoRepository.save(facturaHasProducto);

        productoHasPuntoVentaService.restarUnidadProductoPuntoVenta(idProducto, idPuntoVenta);
        actualizarTotalFactura(idFactura, idProducto, cantidad);
    }

    /**
     * Agrega múltiples productos a una factura y actualiza los totales.
     *
     * @param listadoProductos lista de productos a agregar.
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
     * @param idFactura identificador de la factura.
     * @param idProducto identificador del producto.
     * @return true si el producto se eliminó correctamente, false en caso contrario.
     */
    public boolean removeItem(Long idFactura, Long idProducto) {
        if (!facturaRepository.existsById(idFactura)) {
            throw new IllegalArgumentException("La factura no existe", new Exception("404"));
        }
        if (!facturaHasProductoRepository.existsByIdFacturaAndIdProducto(idFactura, idProducto)) {
            throw new IllegalArgumentException("El producto no corresponde a esa factura", new Exception("404"));
        }

        FacturaHasProducto factura = facturaHasProductoRepository
            .findByIdFacturaAndIdProducto(idFactura, idProducto)
            .orElseThrow();

        try {
            facturaHasProductoRepository.delete(factura);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}