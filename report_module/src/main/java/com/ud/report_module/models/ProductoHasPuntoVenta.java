package com.ud.report_module.models;

import jakarta.persistence.*;

/**
 * Entidad que representa la relación entre un producto y un punto de venta.
 * Permite especificar la cantidad de un producto disponible en un punto de venta específico.
 */
@Entity
@Table(name = "productos_has_puntos_venta", schema = "artesanias_bogota") // Especifica la tabla y el esquema de la base de datos.
public class ProductoHasPuntoVenta {

    // Identificador único de la relación.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Identificador del producto asociado.
    @Column(name = "productos_id", nullable = false)
    private Long idProducto;

    // Relación con la entidad Producto.
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con Producto.
    @JoinColumn(name = "productos_id", insertable = false, updatable = false) // Mapeo con la columna 'productos_id'.
    private Producto producto;

    // Identificador del punto de venta asociado.
    @Column(name = "puntos_venta_id", nullable = false)
    private Long idPuntoVenta;

    // Relación con la entidad PuntoVenta.
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con PuntoVenta.
    @JoinColumn(name = "puntos_venta_id", insertable = false, updatable = false) // Mapeo con la columna 'puntos_venta_id'.
    private PuntoVenta puntoVenta;

    // Cantidad del producto en el punto de venta.
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    /**
     * Constructor por defecto.
     */
    public ProductoHasPuntoVenta() {
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador de la relación.
     *
     * @return El identificador de la relación.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la relación.
     *
     * @param id El identificador de la relación.
     */
    public void setId(Long id) {
        this.id = id;
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
     * Obtiene el producto asociado.
     *
     * @return El producto asociado.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece el producto asociado.
     *
     * @param producto El producto asociado.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
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
     * Obtiene el punto de venta asociado.
     *
     * @return El punto de venta asociado.
     */
    public PuntoVenta getPuntoVenta() {
        return puntoVenta;
    }

    /**
     * Establece el punto de venta asociado.
     *
     * @param puntoVenta El punto de venta asociado.
     */
    public void setPuntoVenta(PuntoVenta puntoVenta) {
        this.puntoVenta = puntoVenta;
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