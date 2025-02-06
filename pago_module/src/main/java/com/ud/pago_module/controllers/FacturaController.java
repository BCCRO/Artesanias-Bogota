package com.ud.pago_module.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import com.ud.pago_module.models.Factura;
import com.ud.pago_module.models.dtos.FacturaDTO;
import com.ud.pago_module.models.dtos.FacturaHasProductoDTO;
import com.ud.pago_module.models.responses.ServerErrorResponse;
import com.ud.pago_module.services.FacturaHasProductoService;
import com.ud.pago_module.services.FacturaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controlador REST para gestionar facturas.
 * Proporciona endpoints para CRUD de facturas y gestión de productos asociados.
 */
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private FacturaHasProductoService facturaHasProductoService;

    /**
     * Obtiene una lista de todas las facturas.
     *
     * @return Una lista de facturas o un código de estado 204 si no hay facturas.
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
     * Obtiene una factura específica por su ID.
     *
     * @param id ID de la factura.
     * @return La factura correspondiente o un código de estado 204 si no se encuentra.
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
     * @param facturaDTO DTO con los datos necesarios para crear la factura.
     * @return La factura creada o un código de estado 500 en caso de error.
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
     * Agrega un producto a una factura existente.
     *
     * @param facturaHasProductoDTO DTO con los datos del producto y la factura.
     * @return Código de estado 200 si el producto se agrega correctamente.
     */
    @PostMapping(value = "/agregar-producto", produces = "application/json")
    public ResponseEntity<Void> agregarProducto(@RequestBody FacturaHasProductoDTO facturaHasProductoDTO) {
        try {
            facturaHasProductoService.anadirProductoFactura(
//                facturaHasProductoDTO.getIdPuntoVenta(),
                facturaHasProductoDTO.getIdFactura(),
                facturaHasProductoDTO.getIdProducto(),
                facturaHasProductoDTO.getCantidad()
            );
        } catch (Exception ex) {
            System.out.println(String.format("Error añadiendo un producto a la factura: %s - Error: %s", 
                facturaHasProductoDTO.getIdFactura(), ex.getMessage()));
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Agrega múltiples productos a una factura.
     *
     * @param ListProductos Lista de productos a agregar.
     * @return Código de estado 200 si los productos se agregan correctamente.
     */
    @PostMapping(value = "/agregar-productos", produces = "application/json")
    public ResponseEntity<Void> agregarProductos(@RequestBody List<FacturaHasProductoDTO> ListProductos) {
        try {
            facturaHasProductoService.anadirProductosFactura(ListProductos);
        } catch (Exception ex) {
            System.out.println(String.format("Error añadiendo los productos: %s", ex.getMessage()));
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Actualiza el estado de una factura.
     *
     * @param facturaId ID de la factura.
     * @param estado Nuevo estado de la factura.
     * @return Mensaje de confirmación o error.
     */
    @PutMapping(value = "/update/state/{id}/{estado}", produces = "application/json")
    public ResponseEntity<?> actualizarEstado(@PathVariable("id") String facturaId, @PathVariable String estado) {
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
     * @param idItem ID del producto.
     * @return Mensaje de confirmación o error.
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

    @GetMapping("/obtener-token")
    public String getMethodName() {
      facturaHasProductoService.anadirProductoFactura(null, null, 0);
      return new String();
    }
    
}