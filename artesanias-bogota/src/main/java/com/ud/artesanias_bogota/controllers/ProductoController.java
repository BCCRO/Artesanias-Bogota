package com.ud.artesanias_bogota.controllers;

import com.ud.artesanias_bogota.models.Producto;
import com.ud.artesanias_bogota.models.dtos.ProductoCategoriaProductoDTO;
import com.ud.artesanias_bogota.models.dtos.ProductoDTO;
import com.ud.artesanias_bogota.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    /**
     * Extraemos todos los productos de la DB
     * @return         Listado de productos
     */
    @GetMapping(value="/productos", produces = "application/json")
    public ResponseEntity<List<Producto>> getAllProducts(){
      System.out.println("HOLAS");
        List<Producto> productos = productoService.findAllProducto();

        if(productos == null || productos.isEmpty()) ResponseEntity.noContent().build();

        return ResponseEntity.ok(productos);
    }


    /**
     * Obtenemos un producto por medio de su Id
     * @param id    id del producto
     * @return      producto
     */
    @GetMapping(value = "/producto/{id}", produces = "application/json")
    public ResponseEntity<Producto> getProductById(@PathVariable Long id){

        Optional<Producto> producto = productoService.findProductoById(id);

        if(producto.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(producto.get());
    }


    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<Producto> createProduct(@RequestBody ProductoDTO productoDTO){

        Producto producto = null;

        try{
            producto = productoService.createProducto(productoDTO);
        }
        catch (Exception ex){
            System.out.println("Error en createProduct: " + ex.getMessage()); // Cambiar a logger
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(producto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> putMethodName(@PathVariable Long id, @RequestBody ProductoDTO producto) {
        //TODO: process PUT request
        try {
          Producto newProducto = productoService.updateProducto(id, producto);
          
          return ResponseEntity.status(202).body(newProducto);

        } catch(IllegalArgumentException e) {
          return ResponseEntity.status(409).body(e.getLocalizedMessage());
        }catch (Exception e) {
          return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping(value = "/change_state/{id}/{estado}", produces = "application/json")
    public ResponseEntity<?> deactivateProduct(@PathVariable Long id, @PathVariable String estado){
      try {
        boolean resultado = productoService.deactivate(id,estado);
        if (!resultado)throw new Exception("Error al actualizar el estado");
        return ResponseEntity.status(200).body("Estate changed succesfully");
      } catch(RuntimeException e){
        return ResponseEntity.status(404).body("Product not found");
      }catch (Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
      }
      

    }

}
