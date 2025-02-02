package com.ud.inventario_module.services;

import com.ud.inventario_module.models.Pedido;
import com.ud.inventario_module.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoAsyncService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Async
    public void updateStateAutomatically(Pedido pedido, String estado, Long timeSleep) {

        try {
            Thread.sleep(timeSleep);
            pedido.setEstado(estado);

            System.out.println("Actualizando el estado del pedido: " + pedido.getId());
            pedidoRepository.save(pedido);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
