package com.ud.inventario_module.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Entidad que representa una transacción en el sistema.
 * Está mapeada a la tabla "transacciones" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "transacciones", schema = "artesanias_bogota")
public class Transaccion {

    // Identificador único de la transacción.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha de creación de la transacción.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    // Fecha de la última actualización de la transacción.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    // Estado de la transacción.
    @Column(name = "estado", nullable = false)
    private String estado;

    // Identificador de la transacción en el portal de pagos externo.
    @Column(name = "id_portal_pagos")
    private Long idPortalPagos;

    // Identificador del medio de pago utilizado en la transacción.
    @Column(name = "medios_pago_id", nullable = false)
    private Long idMedioPago;

    // Relación con la entidad MedioPago.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medios_pago_id", insertable = false, updatable = false)
    private MedioPago medioPago;

    // Relación con las facturas asociadas a la transacción.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private Set<Factura> facturas;

    /**
     * Constructor vacío de la clase Transaccion.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public Transaccion() {
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador único de la transacción.
     * 
     * @return el identificador de la transacción.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la transacción.
     * 
     * @param id el identificador de la transacción a establecer.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha de creación de la transacción.
     * 
     * @return la fecha de creación.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de creación de la transacción.
     * 
     * @param fecha la fecha de creación a establecer.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la fecha de la última actualización de la transacción.
     * 
     * @return la fecha de actualización.
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Establece la fecha de la última actualización de la transacción.
     * 
     * @param fechaActualizacion la fecha de actualización a establecer.
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Obtiene el estado de la transacción.
     * 
     * @return el estado de la transacción.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la transacción.
     * 
     * @param estado el estado de la transacción a establecer.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el identificador de la transacción en el portal de pagos externo.
     * 
     * @return el identificador en el portal de pagos.
     */
    public Long getIdPortalPagos() {
        return idPortalPagos;
    }

    /**
     * Establece el identificador de la transacción en el portal de pagos externo.
     * 
     * @param idPortalPagos el identificador en el portal de pagos a establecer.
     */
    public void setIdPortalPagos(Long idPortalPagos) {
        this.idPortalPagos = idPortalPagos;
    }

    /**
     * Obtiene el identificador del medio de pago utilizado en la transacción.
     * 
     * @return el identificador del medio de pago.
     */
    public Long getIdMedioPago() {
        return idMedioPago;
    }

    /**
     * Establece el identificador del medio de pago utilizado en la transacción.
     * 
     * @param idMedioPago el identificador del medio de pago a establecer.
     */
    public void setIdMedioPago(Long idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    /**
     * Obtiene la entidad MedioPago asociada a la transacción.
     * 
     * @return el medio de pago asociado.
     */
    public MedioPago getMedioPago() {
        return medioPago;
    }

    /**
     * Establece la entidad MedioPago asociada a la transacción.
     * 
     * @param medioPago el medio de pago a asociar.
     */
    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }

    /**
     * Obtiene el conjunto de facturas asociadas a la transacción.
     * 
     * @return un conjunto de facturas.
     */
    public Set<Factura> getFacturas() {
        return facturas;
    }

    /**
     * Establece el conjunto de facturas asociadas a la transacción.
     * 
     * @param facturas el conjunto de facturas a asociar.
     */
    public void setFacturas(Set<Factura> facturas) {
        this.facturas = facturas;
    }
}