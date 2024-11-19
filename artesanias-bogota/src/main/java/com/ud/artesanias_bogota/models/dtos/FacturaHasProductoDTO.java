package com.ud.artesanias_bogota.models.dtos;

public class FacturaHasProductoDTO {

    private Long idFactura;
    private Long idProducto;
    private int cantidad;

    public FacturaHasProductoDTO() {
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
