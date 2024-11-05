package com.ud.artesanias_bogota.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "transacciones", schema = "artesanias_bogota")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha", nullable = false)
    private Date fecha;

    @Column(name="estado", nullable = false)
    private int estado;

    @Column(name = "id_portal_pagos")
    private Long idPortalPagos;

    @Column(name = "medios_pago_id", nullable = false)
    private Long idMedioPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medios_pago_id", insertable = false, updatable = false)
    private MedioPago medioPago;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private Set<Factura> facturas;

    public Transaccion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Set<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(Set<Factura> facturas) {
        this.facturas = facturas;
    }

    public Long getIdPortalPagos() {
        return idPortalPagos;
    }

    public void setIdPortalPagos(Long idPortalPagos) {
        this.idPortalPagos = idPortalPagos;
    }

    public Long getIdMedioPago() {
        return idMedioPago;
    }

    public void setIdMedioPago(Long idMedioPago) {
        this.idMedioPago = idMedioPago;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }
}
