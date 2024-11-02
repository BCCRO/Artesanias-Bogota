package com.ud.artesanias_bogota.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="medios_pago")
public class MedioPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", unique = true, nullable = false)
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private Set<Transaccion> transacciones;

    public MedioPago() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(Set<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}
