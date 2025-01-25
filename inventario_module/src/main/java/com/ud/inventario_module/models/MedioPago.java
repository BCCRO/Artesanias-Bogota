package com.ud.inventario_module.models;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa un medio de pago en el sistema.
 * Está mapeada a la tabla "medios_pago" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "medios_pago", schema = "artesanias_bogota")
public class MedioPago {

    // Identificador único del medio de pago.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del medio de pago, debe ser único y no nulo.
    @Column(name = "nombre", unique = true, nullable = false)
    private String nombre;

    // Relación uno a muchos con la entidad Transaccion.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private Set<Transaccion> transacciones;

    /**
     * Constructor vacío de la clase MedioPago.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public MedioPago() {
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador único del medio de pago.
     * 
     * @return el identificador del medio de pago.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del medio de pago.
     * 
     * @param id el identificador del medio de pago a establecer.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del medio de pago.
     * 
     * @return el nombre del medio de pago.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del medio de pago.
     * 
     * @param nombre el nombre del medio de pago a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el conjunto de transacciones asociadas a este medio de pago.
     * 
     * @return un conjunto de transacciones.
     */
    public Set<Transaccion> getTransacciones() {
        return transacciones;
    }

    /**
     * Establece el conjunto de transacciones asociadas a este medio de pago.
     * 
     * @param transacciones el conjunto de transacciones a establecer.
     */
    public void setTransacciones(Set<Transaccion> transacciones) {
        this.transacciones = transacciones;
    }
}