package com.ud.inventario_module.controllers;

import com.ud.inventario_module.models.Pedido;
import com.ud.inventario_module.models.dtos.PedidoDTO;
import com.ud.inventario_module.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController                     // Declara esta clase como un controlador REST para manejar solicitudes relacionadas con pagos.
@RequestMapping("/api/pedidos")         // Define la ruta base "/api/pedidos" para las solicitudes dirigidas a este controlador.
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

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

}
