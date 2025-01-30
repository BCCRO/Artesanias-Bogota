package com.ud.inventario_module.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.inventario_module.models.Factura;
import com.ud.inventario_module.models.dtos.FacturaDTO;
import com.ud.inventario_module.models.dtos.FacturaHasProductoDTO;
import com.ud.inventario_module.models.responses.ServerErrorResponse;
import com.ud.inventario_module.services.FacturaHasProductoService;
import com.ud.inventario_module.services.FacturaService;

@RestController // Declara esta clase como un controlador REST para manejar solicitudes relacionadas con facturas.
@RequestMapping("/api/facturas") // Define la ruta base "/api/facturas" para las solicitudes dirigidas a este controlador.
public class FacturaController {

    @Autowired // Inyecta la dependencia del servicio de facturas.
    private FacturaService facturaService;

    @Autowired // Inyecta la dependencia del servicio de relación entre facturas y productos.
    private FacturaHasProductoService facturaHasProductoService;

    @GetMapping("") // Define un endpoint para obtener todas las facturas.
    public ResponseEntity<?> getAllFacturas() {
        // Obtiene la lista de facturas desde el servicio.
        List<Factura> facturas = facturaService.obtenerFacturas();
        // Verifica si la lista está vacía y retorna un estado 204 (Sin contenido).
        if (facturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Retorna la lista de facturas con un estado 200 (OK).
        return ResponseEntity.ok(facturas);
    }

    @GetMapping(value = "/factura/{id}", produces = "application/json") // Define un endpoint para obtener una factura por su ID.
    public ResponseEntity<Factura> getFacturaById(@PathVariable Long id) {
        // Busca la factura en el servicio por su ID.
        Optional<Factura> factura = facturaService.obtenerFactura(id);
        // Si no se encuentra, retorna un estado 204 (Sin contenido).
        if (factura.isEmpty()) return ResponseEntity.noContent().build();
        // Si se encuentra, retorna la factura con un estado 200 (OK).
        return ResponseEntity.ok(factura.get());
    }

    @PostMapping(value = "/crear", produces = "application/json") // Define un endpoint para crear una nueva factura.
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDTO facturaDTO) {
        Factura factura = null;
        try {
            // Crea una factura utilizando el servicio y los datos del DTO.
            factura = facturaService.crearFactura(facturaDTO.getDocumentoUsuario());
        } catch (Exception ex) {
            // Registra el error en la consola (pendiente de cambio a un logger).
            System.out.println("Error en crearFactura: " + ex.getMessage());
            // Retorna un estado 500 (Error interno del servidor).
            return ResponseEntity.internalServerError().build();
        }
        // Retorna la factura creada con un estado 200 (OK).
        return ResponseEntity.ok(factura);
    }

    @PostMapping(value = "/agregar-producto", produces = "application/json") // Define un endpoint para agregar un producto a una factura.
    public ResponseEntity<?> agregarProducto(@RequestBody FacturaHasProductoDTO facturaHasProductoDTO) {
        try {
            // Llama al servicio para agregar un producto a la factura.
            facturaHasProductoService.anadirProductoFactura(
                facturaHasProductoDTO.getIdPuntoVenta(),
                facturaHasProductoDTO.getIdFactura(),
                facturaHasProductoDTO.getIdProducto(),
                facturaHasProductoDTO.getCantidad()
            );
        } catch (Exception ex) {
            // Maneja errores y retorna un mensaje de error con estado 500.
            String errorMsg = String.format(
                "Error añadiendo un producto a la factura: %s - Error: %s",
                facturaHasProductoDTO.getIdFactura(), ex.getMessage()
            );
            System.out.println(errorMsg);
            return ResponseEntity.internalServerError().body(errorMsg);
        }
        // Retorna un estado 200 (OK) si el producto se agregó correctamente.
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/agregar-productos", produces = "application/json") // Define un endpoint para agregar múltiples productos a una factura.
    public ResponseEntity<?> agregarProductos(@RequestBody List<FacturaHasProductoDTO> ListProductos) {
        try {
            // Llama al servicio para agregar múltiples productos.
            facturaHasProductoService.anadirProductosFactura(ListProductos);
        } catch (Exception ex) {
            // Maneja errores y retorna un mensaje de error con estado 500.
            String errorMsg = String.format("Error añadiendo los productos: %s", ex.getMessage());
            System.out.println(errorMsg);
            return ResponseEntity.internalServerError().body(errorMsg);
        }
        // Retorna un estado 200 (OK) si los productos se agregaron correctamente.
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/update/state/{id}/{estado}", produces = "application/json") // Define un endpoint para actualizar el estado de una factura.
    public ResponseEntity<?> putMethodName(@PathVariable("id") String facturaId, @PathVariable String estado) {
        // Actualiza el estado de la factura utilizando el servicio.
        Boolean resultado = facturaService.actualizarEstado(facturaId, estado.toUpperCase());
        if (!resultado) {
            // Retorna un mensaje de error con estado 500 si la actualización falla.
            return ResponseEntity.internalServerError().body("Error al actualizar el estado");
        }
        // Retorna un mensaje de éxito con estado 200 (OK).
        return ResponseEntity.ok("Se actualizó correctamente el estado");
    }

    @PutMapping(value = "/remove-item/{idFactura}/{idItem}", produces = "application/json") // Define un endpoint para eliminar un producto de una factura.
    public ResponseEntity<?> removeItem(@PathVariable Long idFactura, @PathVariable Long idItem) {
        try {
            // Llama al servicio para eliminar un producto de la factura.
            boolean res = facturaHasProductoService.removeItem(idFactura, idItem);
            if (!res) {
                // Lanza una excepción si la eliminación falla.
                throw new Exception();
            }
            // Retorna un mensaje de éxito con estado 200 (OK).
            return ResponseEntity.ok("Producto eliminado correctamente");
        } catch (IllegalArgumentException e) {
            // Maneja excepciones específicas y retorna el error con el estado adecuado.
            return ResponseEntity.status(Integer.parseInt(e.getCause().getMessage()))
                .body(ServerErrorResponse.builder()
                .statusCode(Integer.parseInt(e.getCause().getMessage()))
                .message(e.getMessage()).build());
        } catch (Exception e) {
            // Maneja errores generales y retorna un estado 500.
            return ResponseEntity.status(500)
                .body(ServerErrorResponse.builder()
                .statusCode(500)
                .message("Hubo un error inesperado").build());
        }
    }
  }