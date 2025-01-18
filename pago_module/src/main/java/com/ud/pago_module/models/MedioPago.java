package com.ud.pago_module.models;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa un medio de pago en el sistema.
 * Cada medio de pago puede estar asociado a múltiples transacciones.
 */
@Entity // Marca esta clase como una entidad JPA.
@Table(name = "medios_pago", schema = "artesanias_bogota") // Especifica la tabla y el esquema de la base de datos.
public class MedioPago {

    // Identificador único del medio de pago.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Nombre del medio de pago.
    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;

    // Relación uno a muchos con la entidad Transaccion.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id") // Relación uno a muchos con la entidad Transaccion.
    private Set<Transaccion> transacciones;

    /**
     * Constructor por defecto.
     */
    public MedioPago() {
    }

    /**
     * Obtiene el identificador del medio de pago.
     *
     * @return El identificador del medio de pago.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del medio de pago.
     *
     * @param id El identificador del medio de pago.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del medio de pago.
     *
     * @return El nombre del medio de pago.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del medio de pago.
     *
     * @param nombre El nombre del medio de pago.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el conjunto de transacciones asociadas a este medio de pago.
     *
     * @return Un conjunto de transacciones.
     */
    public Set<Transaccion> getTransacciones() {
        return transacciones;
    }

    /**
     * Establece el conjunto de transacciones asociadas a este medio de pago.
     *
     * @param transacciones Un conjunto de transacciones.
     */
    public void setTransacciones(Set<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}