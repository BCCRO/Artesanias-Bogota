package com.ud.inventario_module.repositories;

import com.ud.inventario_module.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /**
     * Busca el pedido de una factura
     *
     * @param idFactura identificador de la factura.
     * @return una lista de {@link Pedido} asociadas a la factura.
     */
    List<Pedido> findPedidoByIdFactura(Long idFactura);

}
