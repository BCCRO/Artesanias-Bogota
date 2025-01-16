package com.ud.artesanias_bogota.controllers;

import com.ud.artesanias_bogota.models.Producto;
import com.ud.artesanias_bogota.models.dtos.ProductoDTO;
import com.ud.artesanias_bogota.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestión de productos.
 * Proporciona endpoints para consultar, crear, actualizar y cambiar el estado de los productos.
 */
@RestController // Indica que esta clase es un controlador REST.
@RequestMapping("/api/productos") // Define el prefijo de las rutas para los endpoints relacionados con productos.
public class ProductoController {

    @Autowired // Inyección del servicio de productos.
    private ProductoService productoService;

    /**
     * Obtiene todos los productos de la base de datos.
     * 
     * @return una lista de productos o un estado HTTP 204 si no hay productos disponibles.
     */
    @GetMapping(value = "/productos", produces = "application/json")
    public ResponseEntity<List<Producto>> getAllProducts() {
        System.out.println("Consulta de todos los productos"); // TODO: Cambiar a logger.
        List<Producto> productos = productoService.findAllProducto();

        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto por su ID.
     * 
     * @param id ID del producto a consultar.
     * @return el producto correspondiente o un estado HTTP 204 si no se encuentra.
     */
    @GetMapping(value = "/producto/{id}", produces = "application/json")
    public ResponseEntity<Producto> getProductById(@PathVariable Long id) {
        Optional<Producto> producto = productoService.findProductoById(id);

        if (producto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(producto.get());
    }

    /**
     * Crea un nuevo producto en la base de datos.
     * 
     * @param productoDTO DTO que contiene los datos del producto a crear.
     * @return el producto creado o un estado HTTP 500 en caso de error.
     */
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<Producto> createProduct(@RequestBody ProductoDTO productoDTO) {
        Producto producto;
        try {
            producto = productoService.createProducto(productoDTO);
        } catch (Exception ex) {
            System.out.println("Error en createProduct: " + ex.getMessage()); // TODO: Cambiar a logger.
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(producto);
    }

    /**
     * Actualiza los datos de un producto existente.
     * 
     * @param id ID del producto a actualizar.
     * @param producto DTO con los datos actualizados del producto.
     * @return el producto actualizado o un estado de error si ocurre un problema.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable Long id, @RequestBody ProductoDTO producto) {
        try {
            Producto newProducto = productoService.updateProducto(id, producto);
            return ResponseEntity.status(202).body(newProducto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(e.getLocalizedMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * Cambia el estado de un producto.
     * 
     * @param id ID del producto cuyo estado se actualizará.
     * @param estado nuevo estado del producto.
     * @return un mensaje de éxito o error según el resultado.
     */
    @PutMapping(value = "/change_state/{id}/{estado}", produces = "application/json")
    public ResponseEntity<?> deactivateProduct(@PathVariable Long id, @PathVariable String estado) {
        try {
            boolean resultado = productoService.deactivate(id, estado);
            if (!resultado) {
                throw new Exception("Error al actualizar el estado");
            }
            return ResponseEntity.status(200).body("Estado actualizado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Producto no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}