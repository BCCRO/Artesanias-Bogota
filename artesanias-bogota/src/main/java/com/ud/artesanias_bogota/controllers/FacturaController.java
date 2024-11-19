package com.ud.artesanias_bogota.controllers;

import com.ud.artesanias_bogota.models.Factura;
import com.ud.artesanias_bogota.models.FacturaHasProducto;
import com.ud.artesanias_bogota.models.dtos.FacturaDTO;
import com.ud.artesanias_bogota.models.dtos.FacturaHasProductoDTO;
import com.ud.artesanias_bogota.services.FacturaHasProductoService;
import com.ud.artesanias_bogota.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;
    @Autowired
    private FacturaHasProductoService facturaHasProductoService;

    @GetMapping(value = "/factura/{id}", produces = "application/json")
    public ResponseEntity<Factura> getProductById(@PathVariable Long id){

        Optional<Factura> factura = facturaService.obtenerFactura(id);

        if(factura.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(factura.get());
    }

    @PostMapping(value = "/crear", produces = "application/json")
    public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDTO facturaDTO){

        Factura factura = null;

        try{
            factura = facturaService.crearFactura(facturaDTO.getDocumentoUsuario());
        }
        catch (Exception ex){
            System.out.println("Error en crearFactura: " + ex.getMessage()); // TODO Cambiar a logger
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok(factura);
    }

    @PostMapping(value = "/agregar-producto", produces = "application/json")
    public ResponseEntity<Void> agregarProducto(@RequestBody FacturaHasProductoDTO facturaHasProductoDTO){

        try{
            facturaHasProductoService.anadirProductoFactura(facturaHasProductoDTO.getIdFactura(), +
                    facturaHasProductoDTO.getIdProducto(),
                    facturaHasProductoDTO.getCantidad());
        }
        catch (Exception ex){
            System.out.println(String.format("Error anadiendo un producto a la factura: %s - Error: %s", facturaHasProductoDTO.getIdFactura(), ex.getMessage())); // TODO Cambiar a logger
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/agregar-productos", produces = "application/json")
    public ResponseEntity<Void> agregarProductos(@RequestBody List<FacturaHasProductoDTO> ListProductos){

        try{
            facturaHasProductoService.anadirProductosFactura(ListProductos);
        }
        catch (Exception ex){
            System.out.println(String.format("Error anadiendo los productos : %s", ex.getMessage())); // TODO Cambiar a logger
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }

}
