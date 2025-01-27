package com.ud.pago_module.models;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa una categoría de producto en el sistema.
 * Cada categoría puede estar asociada con múltiples productos.
 */
@Entity // Marca esta clase como una entidad JPA.
@Table(name = "categorias_productos", schema = "artesanias_bogota") // Especifica la tabla y el esquema de la base de datos.
public class CategoriaProducto {

    // Identificador único de la categoría.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Nombre de la categoría.
    @Column(name = "nombre")
    private String nombre;

    // Descripción de la categoría.
    @Column(name = "descripcion")
    private String descripcion;

    // Relación uno a muchos con la entidad Producto.
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id")
    private Set<Producto> productos;

    /**
     * Constructor por defecto.
     */
    public CategoriaProducto() {
    }

    /**
     * Obtiene el identificador de la categoría.
     *
     * @return El identificador de la categoría.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param id El identificador de la categoría.
     */
    public void setId(Long id) {
        this.id = id;
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
     * Obtiene el conjunto de productos asociados a esta categoría.
     *
     * @return Un conjunto de productos.
     */
    public Set<Producto> getProductos() {
        return productos;
    }

    /**
     * Establece el conjunto de productos asociados a esta categoría.
     *
     * @param productos Un conjunto de productos.
     */
    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
}