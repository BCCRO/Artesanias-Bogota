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

import com.ud.inventario_module.models.Producto;
import com.ud.inventario_module.models.dtos.ProductoDTO;
import com.ud.inventario_module.services.ProductoService;

@RestController // Declara esta clase como un controlador REST para manejar solicitudes relacionadas con productos.
@RequestMapping("/api/productos") // Define la ruta base "/api/productos" para las solicitudes dirigidas a este controlador.
public class ProductoController {

    @Autowired // Inyecta la dependencia del servicio de productos.
    ProductoService productoService;

    /**
     * Obtiene todos los productos disponibles en la base de datos.
     * @return Una lista de productos o un estado 204 si no hay productos disponibles.
     */
    @GetMapping(value = "/productos", produces = "application/json")
    public ResponseEntity<List<Producto>> getAllProducts() {
        // Obtiene la lista de productos desde el servicio.
        List<Producto> productos = productoService.findAllProducto();

        // Retorna un estado 204 (Sin contenido) si la lista está vacía o es nula.
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Retorna la lista de productos con estado 200 (OK).
        return ResponseEntity.ok(productos);
    }

    /**
     * Obtiene un producto por su ID.
     * @param id El ID del producto a buscar.
     * @return El producto encontrado o un estado 204 si no existe.
     */
    @GetMapping(value = "/producto/{id}", produces = "application/json")
    public ResponseEntity<Producto> getProductById(@PathVariable Long id) {
        // Busca el producto por su ID utilizando el servicio.
        Optional<Producto> producto = productoService.findProductoById(id);

        // Retorna un estado 204 (Sin contenido) si no se encuentra el producto.
        if (producto.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Retorna el producto encontrado con estado 200 (OK).
        return ResponseEntity.ok(producto.get());
    }

    /**
     * Crea un nuevo producto.
     * @param productoDTO El DTO que contiene los datos del producto a crear.
     * @return El producto creado o un estado 500 en caso de error.
     */
    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<Producto> createProduct(@RequestBody ProductoDTO productoDTO) {
        Producto producto = null;
        try {
            // Llama al servicio para crear un nuevo producto.
            producto = productoService.createProducto(productoDTO);
        } catch (Exception ex) {
            // Registra el error en la consola (pendiente de cambio a logger).
            System.out.println("Error en createProduct: " + ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        // Retorna el producto creado con estado 200 (OK).
        return ResponseEntity.ok(producto);
    }

    /**
     * Actualiza un producto existente.
     * @param id El ID del producto a actualizar.
     * @param producto El DTO con los datos actualizados del producto.
     * @return El producto actualizado o un mensaje de error en caso de falla.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable Long id, @RequestBody ProductoDTO producto) {
        try {
            // Llama al servicio para actualizar el producto.
            Producto newProducto = productoService.updateProducto(id, producto);
            // Retorna el producto actualizado con estado 202 (Aceptado).
            return ResponseEntity.status(202).body(newProducto);
        } catch (IllegalArgumentException e) {
            // Retorna un estado 409 (Conflicto) si hay un error de argumento.
            return ResponseEntity.status(409).body(e.getLocalizedMessage());
        } catch (Exception e) {
            // Retorna un estado 500 (Error interno) para otros errores.
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * Cambia el estado de un producto.
     * @param id El ID del producto a actualizar.
     * @param estado El nuevo estado del producto.
     * @return Un mensaje de éxito o error dependiendo del resultado.
     */
    @PutMapping(value = "/change_state/{id}/{estado}", produces = "application/json")
    public ResponseEntity<?> deactivateProduct(@PathVariable Long id, @PathVariable String estado) {
        try {
            // Cambia el estado del producto utilizando el servicio.
            boolean resultado = productoService.deactivate(id, estado);
            if (!resultado) throw new Exception("Error al actualizar el estado");
            // Retorna un mensaje de éxito con estado 200 (OK).
            return ResponseEntity.status(200).body("Estado cambiado correctamente");
        } catch (RuntimeException e) {
            // Retorna un mensaje de error con estado 404 si el producto no se encuentra.
            return ResponseEntity.status(404).body("Producto no encontrado");
        } catch (Exception e) {
            // Retorna un mensaje de error con estado 500 (Error interno).
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}