package com.ud.pago_module.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

/**
 * Entidad que representa una categoría de punto de venta.
 * Cada categoría puede estar asociada a múltiples puntos de venta.
 */
@Entity // Marca esta clase como una entidad JPA.
@Table(name = "categorias_puntos_venta", schema = "artesanias_bogota") // Especifica la tabla y el esquema en la base de datos.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Ignora propiedades específicas de Hibernate durante la serialización.
public class CategoriaPuntoVenta {

    // Identificador único de la categoría.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long Id;

    // Nombre de la categoría de punto de venta.
    @Column(name = "nombre")
    private String nombre;

    // Descripción de la categoría de punto de venta.
    @Column(name = "descripcion")
    private String descripcion;

    // Relación uno a muchos con la entidad PuntoVenta.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id") // Configura la relación entre entidades.
    private Set<PuntoVenta> puntoVenta;

    /**
     * Constructor por defecto.
     */
    public CategoriaPuntoVenta() {
    }

    /**
     * Obtiene el identificador de la categoría.
     *
     * @return El identificador de la categoría.
     */
    public Long getId() {
        return Id;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param id El identificador de la categoría.
     */
    public void setId(Long id) {
        Id = id;
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return El nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombre El nombre de la categoría.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción de la categoría.
     *
     * @return La descripción de la categoría.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la categoría.
     *
     * @param descripcion La descripción de la categoría.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el conjunto de puntos de venta asociados a esta categoría.
     *
     * @return Un conjunto de puntos de venta.
     */
    public Set<PuntoVenta> getPuntoVenta() {
        return puntoVenta;
    }

    /**
     * Establece el conjunto de puntos de venta asociados a esta categoría.
     *
     * @param puntoVenta Un conjunto de puntos de venta.
     */
    public void setPuntoVenta(Set<PuntoVenta> puntoVenta) {
        this.puntoVenta = puntoVenta;
    }
}