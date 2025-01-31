package com.ud.report_module.models.dtos;

/**
 * DTO (Data Transfer Object) para representar la relación entre un producto y un punto de venta.
 * Se utiliza para transferir información sobre la cantidad de un producto en un punto de venta específico.
 */
public class ProductoPuntoVentaDTO {

    // Identificador del producto.
    private Long idProducto;

    // Identificador del punto de venta.
    private Long idPuntoVenta;

    // Cantidad de producto disponible en el punto de venta.
    private int cantidad;

    /**
     * Constructor por defecto.
     */
    public ProductoPuntoVentaDTO() {
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return El identificador del producto.
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param idProducto El identificador del producto.
     */
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el identificador del punto de venta.
     *
     * @return El identificador del punto de venta.
     */
    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    /**
     * Establece el identificador del punto de venta.
     *
     * @param idPuntoVenta El identificador del punto de venta.
     */
    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    /**
     * Obtiene la cantidad del producto en el punto de venta.
     *
     * @return La cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto en el punto de venta.
     *
     * @param cantidad La cantidad del producto.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}