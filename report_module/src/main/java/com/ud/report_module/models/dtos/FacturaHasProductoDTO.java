package com.ud.report_module.models.dtos;

/**
 * DTO (Data Transfer Object) para representar la relación entre una factura y un producto.
 * Se utiliza para transferir información entre diferentes capas de la aplicación.
 */
public class FacturaHasProductoDTO {

    // Identificador del punto de venta asociado.
    private Long idPuntoVenta;

    // Identificador de la factura.
    private Long idFactura;

    // Identificador del producto asociado.
    private Long idProducto;

    // Cantidad del producto en la factura.
    private int cantidad;

    /**
     * Constructor por defecto.
     */
    public FacturaHasProductoDTO() {
    }

    /**
     * Obtiene el identificador del punto de venta asociado.
     *
     * @return El identificador del punto de venta.
     */
    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    /**
     * Establece el identificador del punto de venta asociado.
     *
     * @param idPuntoVenta El identificador del punto de venta.
     */
    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    /**
     * Obtiene el identificador de la factura.
     *
     * @return El identificador de la factura.
     */
    public Long getIdFactura() {
        return idFactura;
    }

    /**
     * Establece el identificador de la factura.
     *
     * @param idFactura El identificador de la factura.
     */
    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    /**
     * Obtiene el identificador del producto asociado.
     *
     * @return El identificador del producto.
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto asociado.
     *
     * @param idProducto El identificador del producto.
     */
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene la cantidad del producto en la factura.
     *
     * @return La cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto en la factura.
     *
     * @param cantidad La cantidad del producto.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}