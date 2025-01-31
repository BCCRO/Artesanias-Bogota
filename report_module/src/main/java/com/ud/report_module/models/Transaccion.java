package com.ud.report_module.models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Entidad que representa una transacción en el sistema.
 * Una transacción está asociada a un medio de pago y puede estar vinculada a múltiples facturas.
 */
@Entity
@Table(name = "transacciones", schema = "artesanias_bogota") // Especifica la tabla y el esquema en la base de datos.
public class Transaccion {

    // Identificador único de la transacción.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Fecha en que se realizó la transacción.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    // Fecha de la última actualización de la transacción.
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    // Estado actual de la transacción (ejemplo: PENDIENTE, COMPLETADA, CANCELADA).
    @Column(name = "estado", nullable = false)
    private String estado;

    // Identificador del portal de pagos asociado.
    @Column(name = "id_portal_pagos")
    private Long idPortalPagos;

    // Identificador del medio de pago asociado.
    @Column(name = "medios_pago_id", nullable = false)
    private Long idMedioPago;

    // Relación con la entidad MedioPago.
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con MedioPago.
    @JoinColumn(name = "medios_pago_id", insertable = false, updatable = false) // Mapeo con la columna 'medios_pago_id'.
    private MedioPago medioPago;

    // Relación con las facturas asociadas a esta transacción.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id") // Relación uno a muchos con Factura.
    private Set<Factura> facturas;

    /**
     * Constructor por defecto.
     */
    public Transaccion() {
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador único de la transacción.
     *
     * @return El identificador de la transacción.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la transacción.
     *
     * @param id El identificador de la transacción.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha en que se realizó la transacción.
     *
     * @return La fecha de la transacción.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha en que se realizó la transacción.
     *
     * @param fecha La fecha de la transacción.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene la fecha de la última actualización de la transacción.
     *
     * @return La fecha de la última actualización.
     */
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    /**
     * Establece la fecha de la última actualización de la transacción.
     *
     * @param fechaActualizacion La fecha de la última actualización.
     */
    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    /**
     * Obtiene el estado actual de la transacción.
     *
     * @return El estado de la transacción.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual de la transacción.
     *
     * @param estado El estado de la transacción.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el conjunto de facturas asociadas a la transacción.
     *
     * @return El conjunto de facturas asociadas.
     */
    public Set<Factura> getFacturas() {
        return facturas;
    }

    /**
     * Establece el conjunto de facturas asociadas a la transacción.
     *
     * @param facturas El conjunto de facturas asociadas.
     */
    public void setFacturas(Set<Factura> facturas) {
        this.facturas = facturas;
    }

    /**
     * Obtiene el identificador del portal de pagos asociado.
     *
     * @return El identificador del portal de pagos.
     */
    public Long getIdPortalPagos() {
        return idPortalPagos;
    }

    /**
     * Establece el identificador del portal de pagos asociado.
     *
     * @param idPortalPagos El identificador del portal de pagos.
     */
    public void setIdPortalPagos(Long idPortalPagos) {
        this.idPortalPagos = idPortalPagos;
    }

    /**
     * Obtiene el identificador del medio de pago asociado.
     *
     * @return El identificador del medio de pago.
     */
    public Long getIdMedioPago() {
        return idMedioPago;
    }

    /**
     * Establece el identificador del medio de pago asociado.
     *
     * @param idMedioPago El identificador del medio de pago.
     */
    public void setIdMedioPago(Long idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    /**
     * Obtiene el medio de pago asociado a la transacción.
     *
     * @return El medio de pago asociado.
     */
    public MedioPago getMedioPago() {
        return medioPago;
    }

    /**
     * Establece el medio de pago asociado a la transacción.
     *
     * @param medioPago El medio de pago asociado.
     */
    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }
}