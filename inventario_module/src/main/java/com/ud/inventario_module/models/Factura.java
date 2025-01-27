package com.ud.inventario_module.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Entidad que representa una factura en el sistema.
 * Está mapeada a la tabla "facturas" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "facturas", schema = "artesanias_bogota")
public class Factura {

    // Identificador único de la factura.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha de emisión de la factura.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;

    // Total de la factura sin impuestos ni descuentos.
    @Column(name = "total", nullable = false)
    private Long total;

    // Total de impuestos aplicados a la factura.
    @Column(name = "total_impuesto", nullable = false)
    private Long totalImpuesto;

    // Total de descuentos aplicados a la factura.
    @Column(name = "total_descuento")
    private Long totaldescuento;

    // Documento del usuario asociado a la factura.
    @Column(name = "usuarios_documento", nullable = false)
    private String idUsuarioDocumento;

    // Estado de la factura.
    @Column(name = "estado", nullable = false)
    private String estado;

    /**
     * Relación con el usuario asociado a la factura.
     * Utiliza LAZY fetch para evitar cargar automáticamente la entidad completa.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_documento", insertable = false, updatable = false)
    private Usuario usuarioDocumento;

    // Identificador de la transacción asociada a la factura.
    @Column(name = "transacciones_id")
    private Long transaccionId;

    /**
     * Relación con la transacción asociada a la factura.
     * Utiliza LAZY fetch para evitar cargar automáticamente la entidad completa.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacciones_id", insertable = false, updatable = false)
    private Transaccion transaccion;

    // Relación uno a muchos con los productos asociados a la factura.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idFactura")
    private Set<FacturaHasProducto> productosFacturas;

    /**
     * Constructor vacío de la clase Factura.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public Factura() {
    }

    // Métodos getter y setter con comentarios.

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

    public void setTransaccionId(Long transaccionId) {
        this.transaccionId = transaccionId;
    }

    public Transaccion getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public Set<FacturaHasProducto> getProductosFacturas() {
        return productosFacturas;
    }

    public void setProductosFacturas(Set<FacturaHasProducto> productosFacturas) {
        this.productosFacturas = productosFacturas;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
