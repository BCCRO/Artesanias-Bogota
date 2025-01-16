package com.ud.login_module.models.dtos;

public class ProductoPuntoVentaDTO {

    private Long idProducto;
    private Long idPuntoVenta;
    private int cantidad;

    public ProductoPuntoVentaDTO() {
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
