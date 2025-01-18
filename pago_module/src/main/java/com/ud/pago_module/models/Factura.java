package com.ud.pago_module.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

/**
 * Entidad que representa una factura en el sistema.
 * Contiene información sobre el usuario, los productos, la transacción asociada y los totales.
 */
@Entity // Marca esta clase como una entidad JPA.
@Table(name = "facturas", schema = "artesanias_bogota") // Especifica la tabla y el esquema en la base de datos.
public class Factura {

    // Identificador único de la factura.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Fecha y hora de emisión de la factura.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;

    // Total de la factura.
    @Column(name = "total", nullable = false)
    private Long total;

    // Total de impuestos aplicados en la factura.
    @Column(name = "total_impuesto", nullable = false)
    private Long totalImpuesto;

    // Total de descuentos aplicados en la factura.
    @Column(name = "total_descuento")
    private Long totaldescuento;

    // Documento del usuario asociado a la factura.
    @Column(name = "usuarios_documento", nullable = false)
    private String idUsuarioDocumento;

    // Estado actual de la factura.
    @Column(name = "estado", nullable = false)
    private String estado;

    // Relación con el usuario asociado a la factura (mapeo con clave externa).
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_documento", insertable = false, updatable = false)
    private Usuario usuarioDocumento;

    // Identificador de la transacción asociada a la factura.
    @Column(name = "transacciones_id")
    private Long transaccionId;

    /**
     * Relación con la transacción asociada a la factura.
     * Nota: Existe un problema con la carga de esta relación que puede causar un bucle infinito.
     * Para evitarlo, se utiliza `@JsonIgnore` y `FetchType.LAZY`.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacciones_id", insertable = false, updatable = false)
    private Transaccion transaccion;

    // Relación uno a muchos con los productos asociados a la factura.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idFactura")
    private Set<FacturaHasProducto> productosFacturas;

    /**
     * Constructor por defecto.
     */
    public Factura() {
    }

    // Métodos getter y setter.

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