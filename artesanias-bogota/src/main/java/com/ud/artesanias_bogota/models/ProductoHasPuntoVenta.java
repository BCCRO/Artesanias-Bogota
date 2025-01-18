package com.ud.artesanias_bogota.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;

/**
 * Entidad que representa la relación entre productos y puntos de venta en el sistema.
 * Está mapeada a la tabla "productos_has_puntos_venta" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "productos_has_puntos_venta", schema = "artesanias_bogota")
public class ProductoHasPuntoVenta {

    // Identificador único de la relación.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador del producto asociado.
    @Column(name = "productos_id", nullable = false)
    private Long idProducto;

    // Relación con la entidad Producto.
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productos_id", insertable = false, updatable = false)
    private Producto producto;

    // Identificador del punto de venta asociado.
    @Column(name = "puntos_venta_id", nullable = false)
    private Long idPuntoVenta;

    // Relación con la entidad PuntoVenta.
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puntos_venta_id", insertable = false, updatable = false)
    private PuntoVenta puntoVenta;

    // Cantidad del producto disponible en el punto de venta.
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    // Fecha de la última actualización de esta relación.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    /**
     * Constructor vacío de la clase ProductoHasPuntoVenta.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public ProductoHasPuntoVenta() {
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador único de la relación.
     * 
     * @return el identificador de la relación.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la relación.
     * 
     * @param id el identificador de la relación a establecer.
     */
    public void setId(Long id) {
        this.id = id;
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
     * Obtiene la entidad Producto asociada.
     * 
     * @return el producto asociado.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Establece la entidad Producto asociada.
     * 
     * @param producto el producto a asociar.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
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
     * Obtiene la entidad PuntoVenta asociada.
     * 
     * @return el punto de venta asociado.
     */
    public PuntoVenta getPuntoVenta() {
        return puntoVenta;
    }

    /**
     * Establece la entidad PuntoVenta asociada.
     * 
     * @param puntoVenta el punto de venta a asociar.
     */
    public void setPuntoVenta(PuntoVenta puntoVenta) {
        this.puntoVenta = puntoVenta;
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

    /**
     * Obtiene la fecha de la última actualización de esta relación.
     * 
     * @return la fecha de actualización.
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Establece la fecha de la última actualización de esta relación.
     * 
     * @param fechaActualizacion la fecha de actualización a establecer.
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}