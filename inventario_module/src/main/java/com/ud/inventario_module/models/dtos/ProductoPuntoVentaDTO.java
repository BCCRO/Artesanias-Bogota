package com.ud.artesanias_bogota.models.dtos;

/**
 * DTO (Data Transfer Object) para manejar la relación entre un producto y un punto de venta.
 * Contiene los datos necesarios para gestionar el inventario de un producto en un punto de venta.
 */
public class ProductoPuntoVentaDTO {

    // Identificador del producto.
    private Long idProducto;

    // Identificador del punto de venta.
    private Long idPuntoVenta;

    // Cantidad del producto disponible en el punto de venta.
    private int cantidad;

    /**
     * Constructor vacío de la clase ProductoPuntoVentaDTO.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public ProductoPuntoVentaDTO() {
    }

    /**
     * Obtiene el identificador del producto.
     * 
     * @return el identificador del producto.
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     * 
     * @param idProducto el identificador del producto a establecer.
     */
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el identificador del punto de venta.
     * 
     * @return el identificador del punto de venta.
     */
    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    /**
     * Establece el identificador del punto de venta.
     * 
     * @param idPuntoVenta el identificador del punto de venta a establecer.
     */
    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    /**
     * Obtiene la cantidad del producto disponible en el punto de venta.
     * 
     * @return la cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto disponible en el punto de venta.
     * 
     * @param cantidad la cantidad del producto a establecer.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
