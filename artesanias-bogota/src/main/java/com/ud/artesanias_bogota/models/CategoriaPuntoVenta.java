package com.ud.artesanias_bogota.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa una categoría de puntos de venta en el sistema.
 * Está mapeada a la tabla "categorias_puntos_venta" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "categorias_puntos_venta", schema = "artesanias_bogota")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades específicas de Hibernate durante la serialización.

public class CategoriaPuntoVenta {

    // Identificador único de la categoría del punto de venta.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID.
    private Long Id;

    // Nombre de la categoría del punto de venta.
    @Column(name = "nombre")
    private String nombre;

    // Descripción de la categoría del punto de venta.
    @Column(name = "descripcion")
    private String descripcion;

    // Relación uno a muchos con la entidad PuntoVenta.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id") // Lazy loading y mapeo por ID.
    private Set<PuntoVenta> puntoVenta;

    /**
     * Constructor vacío de la clase CategoriaPuntoVenta.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public CategoriaPuntoVenta() {
    }

    /**
     * Obtiene el identificador único de la categoría del punto de venta.
     * 
     * @return el identificador de la categoría.
     */
    public Long getId() {
        return Id;
    }

    /**
     * Establece el identificador único de la categoría del punto de venta.
     * 
     * @param id el identificador de la categoría a establecer.
     */
    public void setId(Long id) {
        Id = id;
    }

    /**
     * Obtiene el nombre de la categoría del punto de venta.
     * 
     * @return el nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría del punto de venta.
     * 
     * @param nombre el nombre de la categoría a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción de la categoría del punto de venta.
     * 
     * @return la descripción de la categoría.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la categoría del punto de venta.
     * 
     * @param descripcion la descripción de la categoría a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el conjunto de puntos de venta asociados a esta categoría.
     * 
     * @return un conjunto de puntos de venta.
     */
    public Set<PuntoVenta> getPuntoVenta() {
        return puntoVenta;
    }

    /**
     * Establece el conjunto de puntos de venta asociados a esta categoría.
     * 
     * @param puntoVenta el conjunto de puntos de venta a establecer.
     */
    public void setPuntoVenta(Set<PuntoVenta> puntoVenta) {
        this.puntoVenta = puntoVenta;
    }
}
