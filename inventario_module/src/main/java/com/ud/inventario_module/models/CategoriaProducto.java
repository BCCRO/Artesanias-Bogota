package com.ud.artesanias_bogota.models;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa una categoría de productos en el sistema.
 * Está mapeada a la tabla "categorias_productos" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "categorias_productos", schema = "artesanias_bogota")
//@NamedEntityGraph(
//        name = "categoriaProducto.producto",
//        attributeNodes = @NamedAttributeNode("productos")
//)
public class CategoriaProducto {

    // Identificador único de la categoría.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID.
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
     * Constructor vacío de la clase CategoriaProducto.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public CategoriaProducto() {
    }

    /**
     * Obtiene el identificador único de la categoría.
     * 
     * @return el identificador de la categoría.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la categoría.
     * 
     * @param id el identificador de la categoría a establecer.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la categoría.
     * 
     * @return el nombre de la categoría.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     * 
     * @param nombre el nombre de la categoría a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción de la categoría.
     * 
     * @return la descripción de la categoría.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la categoría.
     * 
     * @param descripcion la descripción de la categoría a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el conjunto de productos asociados a esta categoría.
     * 
     * @return un conjunto de productos.
     */
    public Set<Producto> getProductos() {
        return productos;
    }

    /**
     * Establece el conjunto de productos asociados a esta categoría.
     * 
     * @param productos el conjunto de productos a establecer.
     */
    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
}