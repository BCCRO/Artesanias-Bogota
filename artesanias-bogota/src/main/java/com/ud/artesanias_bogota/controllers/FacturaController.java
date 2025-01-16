package com.ud.artesanias_bogota.controllers;

import com.ud.artesanias_bogota.models.Factura;
import com.ud.artesanias_bogota.models.dtos.FacturaDTO;
import com.ud.artesanias_bogota.models.dtos.FacturaHasProductoDTO;
import com.ud.artesanias_bogota.models.responses.ServerErrorResponse;
import com.ud.artesanias_bogota.services.FacturaHasProductoService;
import com.ud.artesanias_bogota.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de facturas.
 * Proporciona endpoints para crear, consultar, actualizar y gestionar productos en facturas.
 */
@RestController // Indica que esta clase es un controlador REST.
@RequestMapping("/api/facturas") // Define el prefijo de las rutas para los endpoints de facturas.
public class FacturaController {

    @Autowired // Inyección del servicio de facturas.
    private FacturaService facturaService;

    @Autowired // Inyección del servicio de productos asociados a facturas.
    private FacturaHasProductoService facturaHasProductoService;

    /**
     * Obtiene todas las facturas.
     *
     * @return una lista de todas las facturas registradas o un estado HTTP 204 si no hay datos.
     */
    @GetMapping("")
    public ResponseEntity<?> getAllFacturas() {
        List<Factura> facturas = facturaService.obtenerFacturas();
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facturas);
    }

    /**
     * Obtiene una factura por su ID.
     *
     * @param id el ID de la factura a buscar.
     * @return la factura correspondiente o un estado HTTP 204 si no se encuentra.
     */
    @GetMapping(value = "/factura/{id}", produces = "application/json")
    public ResponseEntity<Factura> getFacturaById(@PathVariable Long id) {
        Optional<Factura> factura = facturaService.obtenerFactura(id);
        if (factura.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(factura.get());
    }

    /**
     * Crea una nueva factura.
     *
     * @param facturaDTO datos de la factura que se va a crear.
     * @return la factura creada o un estado HTTP 500 en caso de error.
     */
    @PostMapping(value = "/crear", produces = "application/json")
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDTO facturaDTO) {
        Factura factura = null;
        try {
            factura = facturaService.crearFactura(facturaDTO.getDocumentoUsuario());
        } catch (Exception ex) {
            System.out.println("Error en crearFactura: " + ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(factura);
    }

    /**
     * Agrega un producto a una factura.
     *
     * @param facturaHasProductoDTO datos del producto y la factura a los que se asociará.
     * @return un estado HTTP 200 si se agrega correctamente o HTTP 500 en caso de error.
     */
    @PostMapping(value = "/agregar-producto", produces = "application/json")
    public ResponseEntity<?> agregarProducto(@RequestBody FacturaHasProductoDTO facturaHasProductoDTO) {
        // Seleccionar dinámicamente el punto de venta desde la API y eliminarlo del DTO.
        try {
            facturaHasProductoService.anadirProductoFactura(
                facturaHasProductoDTO.getIdPuntoVenta(),
                facturaHasProductoDTO.getIdFactura(),
                facturaHasProductoDTO.getIdProducto(),
                facturaHasProductoDTO.getCantidad()
            );
        } catch (Exception ex) {
            String errorMsg = String.format("Error añadiendo un producto a la factura: %s - Error: %s",
                facturaHasProductoDTO.getIdFactura(), ex.getMessage());
            System.out.println(errorMsg);
            return ResponseEntity.internalServerError().body(errorMsg);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Agrega múltiples productos a una factura.
     *
     * @param ListProductos lista de productos a asociar con una factura.
     * @return un estado HTTP 200 si se agregan correctamente o HTTP 500 en caso de error.
     */
    @PostMapping(value = "/agregar-productos", produces = "application/json")
    public ResponseEntity<?> agregarProductos(@RequestBody List<FacturaHasProductoDTO> ListProductos) {
        try {
            facturaHasProductoService.anadirProductosFactura(ListProductos);
        } catch (Exception ex) {
            String errorMsg = String.format("Error añadiendo los productos: %s", ex.getMessage());
            System.out.println(errorMsg);
            return ResponseEntity.internalServerError().body(errorMsg);
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza el estado de una factura.
     *
     * @param facturaId ID de la factura.
     * @param estado nuevo estado de la factura.
     * @return un mensaje de éxito o error según el resultado.
     */
    @PutMapping(value = "/update/state/{id}/{estado}", produces = "application/json")
    public ResponseEntity<?> putMethodName(@PathVariable("id") String facturaId, @PathVariable String estado) {
        Boolean resultado = facturaService.actualizarEstado(facturaId, estado.toUpperCase());
        if (!resultado) {
            return ResponseEntity.internalServerError().body("Error al actualizar el estado");
        }
        return ResponseEntity.ok("Se actualizó correctamente el estado");
    }

    /**
     * Elimina un producto de una factura.
     *
     * @param idFactura ID de la factura.
     * @param idItem ID del producto a eliminar.
     * @return un mensaje de éxito o error según el resultado.
     */
    @PutMapping(value = "/remove-item/{idFactura}/{idItem}", produces = "application/json")
    public ResponseEntity<?> removeItem(@PathVariable Long idFactura, @PathVariable Long idItem) {
        try {
            boolean res = facturaHasProductoService.removeItem(idFactura, idItem);
            if (!res) {
                throw new Exception();
            }
            return ResponseEntity.ok("Producto eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(Integer.parseInt(e.getCause().getMessage()))
                .body(ServerErrorResponse.builder()
                    .statusCode(Integer.parseInt(e.getCause().getMessage()))
                    .message(e.getMessage())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(ServerErrorResponse.builder()
                    .statusCode(500)
                    .message("Hubo un error inesperado")
                    .build());
        }
    }
}

