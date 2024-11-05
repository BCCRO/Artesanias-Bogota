package com.ud.artesanias_bogota.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="facturas", schema = "artesanias_bogota")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_emision", nullable = false)
    private Date fechaEmision;

    @Column(name="total", nullable = false)
    private Long total;

    @Column(name="total_impuesto", nullable = false)
    private Long totalImpuesto;

    @Column(name="total_descuento")
    private Long totaldescuento;

    @Column(name="usuarios_documento", nullable = false)
    private String idUsuarioDocumento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_documento", insertable = false, updatable = false)
    private Usuario usuarioDocumento;

    @Column(name="transacciones_id", nullable = false)
    private Long transaccionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacciones_id", insertable = false, updatable = false)
    private Transaccion transaccion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idFactura")
    private Set<FacturaHasProducto> productosFacturas;

    public Factura() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(Long totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public Long getTotaldescuento() {
        return totaldescuento;
    }

    public void setTotaldescuento(Long totaldescuento) {
        this.totaldescuento = totaldescuento;
    }

    public String getIdUsuarioDocumento() {
        return idUsuarioDocumento;
    }

    public void setIdUsuarioDocumento(String idUsuarioDocumento) {
        this.idUsuarioDocumento = idUsuarioDocumento;
    }

    public Usuario getUsuarioDocumento() {
        return usuarioDocumento;
    }

    public void setUsuarioDocumento(Usuario usuarioDocumento) {
        this.usuarioDocumento = usuarioDocumento;
    }

    public Long getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Long transacionId) {
        this.transaccionId = transacionId;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transacion) {
        this.transaccion = transacion;
    }

    public Set<FacturaHasProducto> getProductosFacturas() {
        return productosFacturas;
    }

    public void setProductosFacturas(Set<FacturaHasProducto> productosFacturas) {
        this.productosFacturas = productosFacturas;
    }
}