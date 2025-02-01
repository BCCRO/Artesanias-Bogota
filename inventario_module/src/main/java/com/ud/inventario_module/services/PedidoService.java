package com.ud.inventario_module.services;

import com.ud.inventario_module.models.Pedido;
import com.ud.inventario_module.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> findPedidoByIdFactura(Long idFactura){
        return pedidoRepository.findPedidoByIdFactura(idFactura);
    }

    public void updateEstadoPedido(Long idPedido, String estado){

        Optional<Pedido> pedidoOpt = pedidoRepository.findById(idPedido);

        if(pedidoOpt.isPresent()){
            Pedido pedido = pedidoOpt.get();
            pedido.setEstado(estado);

            pedidoRepository.save(pedido);
        }
        else throw new RuntimeException(String.format("No se encontro ningun pedido con el id %s", idPedido));
    }

    public void createPedido(Long idFactura, Long idPuntoVenta, Double latEntrega, Double longEntrega){
        Pedido pedido = new Pedido();
        pedido.setIdFactura(idFactura);
        pedido.setIdPuntosVenta(idPuntoVenta);
        pedido.setLatEntrega(latEntrega);
        pedido.setLongEntrega(longEntrega);
        pedido.setEstado("PR");         //Preparacion

        pedidoRepository.save(pedido);
    }

}
