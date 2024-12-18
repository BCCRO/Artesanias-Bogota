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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;
    @Autowired
    private FacturaHasProductoService facturaHasProductoService;


    @GetMapping("")
    public ResponseEntity<?> getAllFacturas() {
      List<Factura> facturas = facturaService.obtenerFacturas();
      if (facturas.isEmpty()){
        return ResponseEntity.noContent().build();
      }
        return ResponseEntity.ok(facturas);
    }
    

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
            facturaHasProductoService.anadirProductoFactura(facturaHasProductoDTO.getIdPuntoVenta(),
                    facturaHasProductoDTO.getIdFactura(),
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

    @PutMapping("update/state/{id}/{estado}")
    public ResponseEntity putMethodName(@PathVariable("id") String facturaId, @PathVariable String estado) {
      
      Boolean resultado = facturaService.actualizarEstado(facturaId,estado.toUpperCase());
      if (!resultado) {
        return ResponseEntity.internalServerError().body("Error al actualizar el estado");
      }
      return ResponseEntity.ok("Se actualizo correctamente el estado");
    }

}
