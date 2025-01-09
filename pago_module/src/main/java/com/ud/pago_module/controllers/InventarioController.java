package com.ud.pago_module.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ud.pago_module.models.ProductoHasPuntoVenta;
import com.ud.pago_module.models.dtos.ProductoPuntoVentaDTO;
import com.ud.pago_module.services.ProductoHasPuntoVentaService;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private ProductoHasPuntoVentaService productoHasPuntoVentaService;

    @PostMapping(value = "/actualizar-inventario/producto-puntoventa", produces = "application/json")
    public ResponseEntity<String> actualizarInventarioByPorductoAndPuntoVenta(@RequestBody ProductoPuntoVentaDTO productoPuntoVentaDTO){
        try{
            productoHasPuntoVentaService.actualizarInventario(productoPuntoVentaDTO.getIdProducto(),
                    productoPuntoVentaDTO.getIdPuntoVenta(),
                    productoPuntoVentaDTO.getCantidad()
            );
            return ResponseEntity.ok("Se actualizo el correctamente el inventario");
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(e.getMessage());
        }
    }

    @GetMapping(value = "/obtener-inventario", produces = "application/json")
    public ResponseEntity<List<ProductoHasPuntoVenta>> getAllInventario(){
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVenta();

        if(listProductoPuntoVenta.isEmpty()){
            System.out.println("No se encontro ningun producto en el inventario"); //TODO Pasar a logger
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(listProductoPuntoVenta);
        }
    }

    @GetMapping(value = "/obtener-inventario-by-producto/{idProducto}", produces = "application/json")
    public ResponseEntity<List<ProductoHasPuntoVenta>> getInventarioByProducto(@PathVariable Long idProducto){
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVentaByIdProducto(idProducto);

        if(listProductoPuntoVenta.isEmpty()){
            System.out.println("No se encontro el producto en el inventario - id producto: " + idProducto); //TODO Pasar a logger
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(listProductoPuntoVenta);
        }
    }

    @GetMapping(value = "/obtener-inventario-by-puntoventa/{idPuntoVenta}", produces = "application/json")
    public ResponseEntity<List<ProductoHasPuntoVenta>> getInventarioByPuntoVenta(@PathVariable Long idPuntoVenta){
        List<ProductoHasPuntoVenta> listProductoPuntoVenta = productoHasPuntoVentaService.getProductosHasPuntoVentaByIdPuntoVenta(idPuntoVenta);

        if(listProductoPuntoVenta.isEmpty()){
            System.out.println("No se encontro ningun producto para el punto de venta: " + idPuntoVenta); //TODO Pasar a logger
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.ok(listProductoPuntoVenta);
        }
    }

}
