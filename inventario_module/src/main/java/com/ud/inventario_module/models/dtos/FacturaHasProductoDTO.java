package com.ud.inventario_module.models.dtos;

/**
 * DTO (Data Transfer Object) para manejar la relación entre una factura y sus productos.
 * Contiene los datos necesarios para asociar productos a una factura en un punto de venta.
 */
public class FacturaHasProductoDTO {

    // Identificador del punto de venta asociado.
    private Long idPuntoVenta;

    // Identificador de la factura asociada.
    private Long idFactura;

    // Identificador del producto asociado.
    private Long idProducto;

    // Cantidad del producto asociada a la factura.
    private int cantidad;

    /**
     * Constructor vacío de la clase FacturaHasProductoDTO.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public FacturaHasProductoDTO() {
    }

    /**
     * Obtiene el identificador del punto de venta asociado.
     * 
     * @return el identificador del punto de venta.
     */
    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    /**
     * Establece el identificador del punto de venta asociado.
     * 
     * @param idPuntoVenta el identificador del punto de venta a establecer.
     */
    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    /**
     * Obtiene el identificador de la factura asociada.
     * 
     * @return el identificador de la factura.
     */
    public Long getIdFactura() {
        return idFactura;
    }

    /**
     * Establece el identificador de la factura asociada.
     * 
     * @param idFactura el identificador de la factura a establecer.
     */
    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    /**
     * Obtiene el identificador del producto asociado.
     * 
     * @return el identificador del producto.
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto asociado.
     * 
     * @param idProducto el identificador del producto a establecer.
     */
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene la cantidad del producto asociada a la factura.
     * 
     * @return la cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad del producto asociada a la factura.
     * 
     * @param cantidad la cantidad del producto a establecer.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}