package com.ud.artesanias_bogota.models;

import jakarta.persistence.*;

@Entity
@Table(name="productos_has_puntos_venta", schema = "artesanias_bogota")
public class ProductoHasPuntoVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="productos_id", nullable = false)
    private Long idProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productos_id", insertable = false, updatable = false)
    private Producto producto;

    @Column(name="puntos_venta_id", nullable = false)
    private Long idPuntoVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puntos_venta_id", insertable = false, updatable = false)
    private PuntoVenta puntoVenta;

    public ProductoHasPuntoVenta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }

    public PuntoVenta getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(PuntoVenta puntoVenta) {
        this.puntoVenta = puntoVenta;
    }
}
