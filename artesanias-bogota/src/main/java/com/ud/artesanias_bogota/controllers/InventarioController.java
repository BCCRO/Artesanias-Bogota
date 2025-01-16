package com.ud.artesanias_bogota.controllers;

import com.ud.artesanias_bogota.models.ProductoHasPuntoVenta;
import com.ud.artesanias_bogota.models.dtos.ProductoPuntoVentaDTO;
import com.ud.artesanias_bogota.services.ProductoHasPuntoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión del inventario.
 * Proporciona endpoints para actualizar y consultar el inventario basado en productos y puntos de venta.
 */
@RestController // Indica que esta clase es un controlador REST.
@RequestMapping("/api/inventario") // Define el prefijo de las rutas para los endpoints de inventario.
public class InventarioController {

    @Autowired // Inyección del servicio que gestiona los productos en puntos de venta.
    private ProductoHasPuntoVentaService productoHasPuntoVentaService;

    /**
     * Actualiza el inventario de un producto en un punto de venta específico.
     *
     * @param productoPuntoVentaDTO DTO que contiene el ID del producto, ID del punto de venta y cantidad a actualizar.
     * @return una respuesta HTTP 200 si se actualiza correctamente o HTTP 500 en caso de error.
     */
    @PostMapping(value = "/actualizar-inventario/producto-puntoventa", produces = "application/json")
    public ResponseEntity<String> actualizarInventarioByPorductoAndPuntoVenta(@RequestBody ProductoPuntoVentaDTO productoPuntoVentaDTO) {
        try {
            productoHasPuntoVentaService.actualizarInventario(
                productoPuntoVentaDTO.getIdProducto(),
                productoPuntoVentaDTO.getIdPuntoVenta(),
                productoPuntoVentaDTO.getCantidad()
            );
            return ResponseEntity.ok("Se actualizó correctamente el inventario");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(e.getMessage());
        }
    }

    /**
     * Obtiene el inventario completo.
     *
     * @return una lista de todos los productos con su información de inventario o HTTP 204 si no hay datos.
     */
    @GetMapping(value = "/obtener-inventario", produces = "application/json")
    public ResponseEntity<List<ProductoHasPuntoVenta>> getAllInventario() {
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVenta();

        if (listProductoPuntoVenta.isEmpty()) {
            System.out.println("No se encontró ningún producto en el inventario");
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(listProductoPuntoVenta);
        }
    }

    /**
     * Obtiene el inventario de un producto específico por su ID.
     *
     * @param idProducto ID del producto a consultar.
     * @return una lista de productos asociados al ID o HTTP 204 si no hay datos.
     */
    @GetMapping(value = "/obtener-inventario-by-producto/{idProducto}", produces = "application/json")
    public ResponseEntity<List<ProductoHasPuntoVenta>> getInventarioByProducto(@PathVariable Long idProducto) {
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVentaByIdProducto(idProducto);

        if (listProductoPuntoVenta.isEmpty()) {
            System.out.println("No se encontró el producto en el inventario - ID producto: " + idProducto); 
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(listProductoPuntoVenta);
        }
    }

    /**
     * Obtiene el inventario de un punto de venta específico por su ID.
     *
     * @param idPuntoVenta ID del punto de venta a consultar.
     * @return una lista de productos asociados al punto de venta o HTTP 204 si no hay datos.
     */
    @GetMapping(value = "/obtener-inventario-by-puntoventa/{idPuntoVenta}", produces = "application/json")
    public ResponseEntity<List<ProductoHasPuntoVenta>> getInventarioByPuntoVenta(@PathVariable Long idPuntoVenta) {
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVentaByIdPuntoVenta(idPuntoVenta);

        if (listProductoPuntoVenta.isEmpty()) {
            System.out.println("No se encontró ningún producto para el punto de venta: " + idPuntoVenta);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(listProductoPuntoVenta);
        }
    }
}
