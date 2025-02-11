package com.ud.inventario_module.controllers;

import com.ud.inventario_module.models.Pedido;
import com.ud.inventario_module.models.dtos.CreatePedidoDTO;
import com.ud.inventario_module.models.dtos.PedidoDTO;
import com.ud.inventario_module.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                     // Declara esta clase como un controlador REST para manejar solicitudes relacionadas con pagos.
@RequestMapping("/api/pedidos")         // Define la ruta base "/api/pedidos" para las solicitudes dirigidas a este controlador.
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/create-pedido")
    public ResponseEntity<Pedido> createPedido(@RequestBody CreatePedidoDTO createPedidoDTO) throws Exception {

        Pedido pedido = pedidoService.createPedido(createPedidoDTO.getIdFactura(), createPedidoDTO.getIdPuntosVenta(), createPedidoDTO.getLatEntrega(), createPedidoDTO.getLongEntrega());


        return ResponseEntity.ok(pedido);
    }

    @GetMapping("/pedido-by-factura/{idFactura}")
    public ResponseEntity<List<Pedido>> pedidoByFactura(@PathVariable Long idFactura) throws Exception {

        List<Pedido> pedidos = pedidoService.findPedidoByIdFactura(idFactura);

        if (pedidos.isEmpty()) {
            System.out.println("No se encontró ningún pedido para la factura");
            return ResponseEntity.noContent().build();
        }


        return ResponseEntity.ok(pedidos);
    }

    @PostMapping("/update-estado-pedido")
    public ResponseEntity<?> updateEstadoPedido(@RequestBody PedidoDTO pedidoDto) throws Exception {

        try {
            pedidoService.updateEstadoPedido(pedidoDto.getIdPedido(), pedidoDto.getEstado());
        }
        catch (RuntimeException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity<?> getPedidos(){
      try {
        return ResponseEntity.ok(pedidoService.getPedidosList());
      } catch (Exception e) {
        System.err.println(e);
        return ResponseEntity.internalServerError().body("Algo salio mal bro");
      }
    }

}
