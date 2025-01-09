package com.ud.pago_module.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ud.pago_module.models.dtos.FacturaHasProductoDTO;

import jakarta.persistence.*;

@Entity
@Table(name = "facturas_has_productos", schema = "artesanias_bogota")
public class FacturaHasProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "facturas_id", nullable = false)
    private Long idFactura;

    /**
     * TODO
     * Tenemos un error en donde se crea un bucle infinito entre la relacion FacturaHasProducto - Factura
     * Deberia solucionarse con el fetch LAZY, pero no es asi
     * forzamos esto para evitar el bucle en la respuesta, pero no podriamos cargar las Factura automaticamente desde FacturaHasProducto cuando lo necesitemos
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facturas_id", insertable = false, updatable = false)
    private Factura factura;

    @Column(name = "productos_id", nullable = false)
    private Long idProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productos_id", insertable = false, updatable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    public FacturaHasProducto() {
    }

    public FacturaHasProducto(FacturaHasProductoDTO dto) {
        this.idFactura = dto.getIdFactura();
        this.idProducto = dto.getIdProducto();
        this.cantidad = dto.getCantidad();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
