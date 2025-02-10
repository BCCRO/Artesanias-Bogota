package com.ud.inventario_module.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.inventario_module.models.ProductoHasPuntoVenta;
import com.ud.inventario_module.models.dtos.ProductoPuntoVentaDTO;
import com.ud.inventario_module.services.ProductoHasPuntoVentaService;
import com.ud.inventario_module.services.PuntoVentaService;

@RestController // Declara esta clase como un controlador REST para manejar solicitudes HTTP relacionadas con el inventario.
@RequestMapping("/api/inventario") // Define la ruta base "/api/inventario" para las solicitudes dirigidas a este controlador.
public class InventarioController {

    @Autowired // Inyecta la dependencia del servicio que maneja la relación entre productos y puntos de venta.
    private ProductoHasPuntoVentaService productoHasPuntoVentaService;

    @Autowired
    private PuntoVentaService puntoVenta;

    @PostMapping(value = "/actualizar-inventario/producto-puntoventa", produces = "application/json") // Define un endpoint para actualizar el inventario basado en el producto y el punto de venta.
    public ResponseEntity<String> actualizarInventarioByPorductoAndPuntoVenta(@RequestBody ProductoPuntoVentaDTO productoPuntoVentaDTO) {
        try {
            // Llama al servicio para actualizar el inventario utilizando los datos proporcionados en el DTO.
            productoHasPuntoVentaService.actualizarInventario(
                productoPuntoVentaDTO.getIdProducto(),
                productoPuntoVentaDTO.getIdPuntoVenta(),
                productoPuntoVentaDTO.getCantidad()
            );
            // Retorna un mensaje de éxito con estado 200 (OK) si la actualización fue exitosa.
            return ResponseEntity.ok("Se actualizó correctamente el inventario");
        } catch (RuntimeException e) {
            // Retorna un mensaje de error con estado 500 si ocurre una excepción.
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(e.getMessage());
        }
    }

    @GetMapping(value = "/obtener-inventario", produces = "application/json") // Define un endpoint para obtener el inventario completo.
    public ResponseEntity<List<ProductoHasPuntoVenta>> getAllInventario() {
        // Obtiene la lista de productos asociados a puntos de venta desde el servicio.
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVenta();

        // Verifica si la lista está vacía y retorna un estado 204 (Sin contenido) si no hay datos.
        if (listProductoPuntoVenta.isEmpty()) {
            System.out.println("No se encontró ningún producto en el inventario");
            return ResponseEntity.noContent().build();
        } else {
            // Retorna la lista de productos con estado 200 (OK) si hay datos disponibles.
            return ResponseEntity.ok(listProductoPuntoVenta);
        }
    }

    @GetMapping(value = "/obtener-inventario-by-producto/{idProducto}", produces = "application/json") // Define un endpoint para obtener inventario por ID de producto.
    public ResponseEntity<List<ProductoHasPuntoVenta>> getInventarioByProducto(@PathVariable Long idProducto) {
        // Obtiene la lista de inventario filtrada por el ID del producto.
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVentaByIdProducto(idProducto);

        // Verifica si la lista está vacía y retorna un estado 204 (Sin contenido) si no hay datos.
        if (listProductoPuntoVenta.isEmpty()) {
            System.out.println("No se encontró el producto en el inventario - ID producto: " + idProducto);
            return ResponseEntity.noContent().build();
        } else {
            // Retorna la lista de productos filtrada con estado 200 (OK).
            return ResponseEntity.ok(listProductoPuntoVenta);
        }
    }

    @GetMapping(value = "/obtener-inventario-total-by-producto/{idProducto}", produces = "application/json") // Define un endpoint para obtener inventario por ID de producto.
    public ResponseEntity<?> getInventarioTotalByProducto(@PathVariable Long idProducto) {
        // Obtiene la lista de inventario filtrada por el ID del producto.
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVentaByIdProducto(idProducto);

        // Verifica si la lista está vacía y retorna un estado 204 (Sin contenido) si no hay datos.
        if (listProductoPuntoVenta.isEmpty()) {
            System.out.println("No se encontró el producto en el inventario - ID producto: " + idProducto);
            return ResponseEntity.noContent().build();
        } else {
            Map<String, Object> mapResponse = new HashMap<>();
            int auxTotalInventario = 0;
            for(ProductoHasPuntoVenta productoHasPuntoVenta : listProductoPuntoVenta){
                auxTotalInventario += productoHasPuntoVenta.getCantidad();
            }
            mapResponse.put("idProducto", idProducto);
            mapResponse.put("totalInventario", auxTotalInventario);
            // Retorna la lista de productos filtrada con estado 200 (OK).
            return ResponseEntity.ok(mapResponse);
        }
    }

    @GetMapping(value = "/obtener-inventario-by-puntoventa/{idPuntoVenta}", produces = "application/json") // Define un endpoint para obtener inventario por ID de punto de venta.
    public ResponseEntity<List<ProductoHasPuntoVenta>> getInventarioByPuntoVenta(@PathVariable Long idPuntoVenta) {
        // Obtiene la lista de inventario filtrada por el ID del punto de venta.
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVentaByIdPuntoVenta(idPuntoVenta);

        // Verifica si la lista está vacía y retorna un estado 204 (Sin contenido) si no hay datos.
        if (listProductoPuntoVenta.isEmpty()) {
            System.out.println("No se encontró ningún producto para el punto de venta: " + idPuntoVenta);
            return ResponseEntity.noContent().build();
        } else {
            // Retorna la lista de productos filtrada con estado 200 (OK).
            return ResponseEntity.ok(listProductoPuntoVenta);
        }
    }

  //Obtiene la lista de puntos de venta cercanos a un usuario
  @GetMapping("/puntos_cercanos/{userId}")
  public ResponseEntity<?> getPuntosCercanos(@PathVariable String userId){
    try {
      return ResponseEntity.ok(puntoVenta.puntosCercanos(userId));
    } catch(RuntimeException e){
      if(e.getMessage().equals("Usuario no encontrado")) return ResponseEntity.status(404).body(e.getMessage());
      return ResponseEntity.status(500).body(e.getMessage());
    }catch (Exception e) {
    return ResponseEntity.status(500).body(e.getMessage());

    }
  }

  @GetMapping("/healthcheck")
  public ResponseEntity<?> getHealthCheck(){
    return ResponseEntity.ok("The Module is UP");
  }

  @GetMapping("/puntos-venta/list")
  public ResponseEntity<?> getPuntosVentaList(){
    try {
      return ResponseEntity.ok(puntoVenta.getPuntosVenta());
    } catch (Exception e) {
      System.out.println(e);
      return ResponseEntity.internalServerError().body("Something went wrong");
    }
  }
}