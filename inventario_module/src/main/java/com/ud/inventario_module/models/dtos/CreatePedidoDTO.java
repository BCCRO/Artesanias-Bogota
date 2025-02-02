package com.ud.inventario_module.models.dtos;

import jakarta.persistence.Column;

public class CreatePedidoDTO {

    private Long idFactura;

    private Long idPuntosVenta;

    private Double latEntrega;

    private Double longEntrega;

    public CreatePedidoDTO() {
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public Long getIdPuntosVenta() {
        return idPuntosVenta;
    }

    public void setIdPuntosVenta(Long idPuntosVenta) {
        this.idPuntosVenta = idPuntosVenta;
    }

    public Double getLatEntrega() {
        return latEntrega;
    }

    public void setLatEntrega(Double latEntrega) {
        this.latEntrega = latEntrega;
    }

    public Double getLongEntrega() {
        return longEntrega;
    }

    public void setLongEntrega(Double longEntrega) {
        this.longEntrega = longEntrega;
    }
}
