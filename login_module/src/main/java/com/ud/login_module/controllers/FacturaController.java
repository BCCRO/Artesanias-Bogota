package com.ud.login_module.controllers;

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

import com.ud.login_module.models.Factura;
import com.ud.login_module.models.dtos.FacturaDTO;
import com.ud.login_module.models.dtos.FacturaHasProductoDTO;
import com.ud.login_module.models.responses.ServerErrorResponse;
import com.ud.login_module.services.FacturaHasProductoService;
import com.ud.login_module.services.FacturaService;

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
    public ResponseEntity<Factura> getFacturaById(@PathVariable Long id){

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
    public ResponseEntity<?> agregarProducto(@RequestBody FacturaHasProductoDTO facturaHasProductoDTO){

        /**
         * TODO Debemos seleccionar el punto de venta dinamicamente con la API
         * y eliminarla del DTO
         */

        try{
            facturaHasProductoService.anadirProductoFactura(facturaHasProductoDTO.getIdPuntoVenta(),
                    facturaHasProductoDTO.getIdFactura(),
                    facturaHasProductoDTO.getIdProducto(),
                    facturaHasProductoDTO.getCantidad());
        }
        catch (Exception ex){
            String errorMsg = String.format("Error anadiendo un producto a la factura: %s - Error: %s", facturaHasProductoDTO.getIdFactura(), ex.getMessage());
            System.out.println(errorMsg); // TODO Cambiar a logger
            return ResponseEntity.internalServerError().body(errorMsg);
        }

        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/agregar-productos", produces = "application/json")
    public ResponseEntity<?> agregarProductos(@RequestBody List<FacturaHasProductoDTO> ListProductos){

        try{
            facturaHasProductoService.anadirProductosFactura(ListProductos);
        }
        catch (Exception ex){
            String errorMsg = String.format("Error anadiendo los productos : %s", ex.getMessage());
            System.out.println(errorMsg); // TODO Cambiar a logger
            return ResponseEntity.internalServerError().body(errorMsg);
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(value="/update/state/{id}/{estado}", produces = "application/json")
    public ResponseEntity<?> putMethodName(@PathVariable("id") String facturaId, @PathVariable String estado) {
      
      Boolean resultado = facturaService.actualizarEstado(facturaId,estado.toUpperCase());
      if (!resultado) {
        return ResponseEntity.internalServerError().body("Error al actualizar el estado");
      }
      return ResponseEntity.ok("Se actualizo correctamente el estado");
    }

    @PutMapping(value="/remove-item/{idFactura}/{idItem}", produces = "application/json")
    public ResponseEntity<?> removeItem(@PathVariable Long idFactura, @PathVariable Long idItem) {
        try {
          boolean res = facturaHasProductoService.removeItem(idFactura,idItem);
          
          if(!res){
            throw new Exception();
          }
          return ResponseEntity.ok("Producto eliminado correctamente");
        } catch(IllegalArgumentException e){
          return ResponseEntity.status(Integer.parseInt(e.getCause().getMessage()))
          .body(ServerErrorResponse.builder().statusCode(Integer.parseInt(e.getCause().getMessage())).message(e.getMessage()).build());
        }catch (Exception e) {
          return ResponseEntity.status(500)
          .body(ServerErrorResponse.builder().statusCode(500).message("Hubo un error inesperado").build());
        }  
        
    }



}
