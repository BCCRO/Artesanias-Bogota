package com.ud.report_module.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa un producto en el sistema.
 * Contiene información relacionada con el producto, su categoría, relaciones con facturas y puntos de venta.
 */
@Entity
@Table(name = "productos", schema = "artesanias_bogota") // Especifica la tabla y el esquema de la base de datos.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita errores relacionados con la inicialización de Hibernate.
public class Producto {

    // Identificador único del producto.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Nombre del producto.
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // URL o representación de la imagen del producto.
    @Column(name = "imagen")
    private String imagen;

    // Precio unitario del producto.
    @Column(name = "precio_unitario", nullable = false)
    private Long precioUnitario;

    // Descripción del producto.
    @Column(name = "descripcion")
    private String descripcion;

    // Calificación del producto (ejemplo: de 1 a 5 estrellas).
    @Column(name = "calificacion", nullable = false)
    private int calificacion;

    // Identificador de la categoría del producto.
    @Column(name = "categorias_productos_id", nullable = false)
    private int idCategoriaProducto;

    // Relación con la categoría del producto.
    @JsonIgnore // Evita la serialización de esta relación para prevenir problemas de bucles infinitos.
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "categorias_productos_id", insertable = false, updatable = false)
    private CategoriaProducto categoriaProducto;
    // Relación uno a muchos con la entidad FacturaHasProducto.

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idProducto")
    private Set<FacturaHasProducto> facturasProducto;

    // Relación uno a muchos con la entidad ProductoHasPuntoVenta.
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idProducto")
    private Set<ProductoHasPuntoVenta> productoPuntosVentas;

    /**
     * Constructor por defecto.
     */
    public Producto() {
    }

    // Métodos getter y setter.

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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public int getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    public void setIdCategoriaProducto(int idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public Set<FacturaHasProducto> getFacturasProducto() {
        return facturasProducto;
    }

    public void setFacturasProducto(Set<FacturaHasProducto> facturasProducto) {
        this.facturasProducto = facturasProducto;
    }

    public Set<ProductoHasPuntoVenta> getProductoPuntosVentas() {
        return productoPuntosVentas;
    }

    public void setProductoPuntosVentas(Set<ProductoHasPuntoVenta> productoPuntosVentas) {
        this.productoPuntosVentas = productoPuntosVentas;
    }
}