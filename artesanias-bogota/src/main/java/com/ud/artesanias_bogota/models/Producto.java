package com.ud.artesanias_bogota.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa un producto en el sistema.
 * Está mapeada a la tabla "productos" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "productos", schema = "artesanias_bogota")
//@NamedEntityGraph(
//        name = "producto.categoriaProducto",
//        attributeNodes = @NamedAttributeNode("categoriaProducto")
//)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita errores de serialización relacionados con proxies de Hibernate.
public class Producto {

    // Identificador único del producto.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del producto, campo obligatorio.
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Imagen asociada al producto, almacenada como cadena.
    @Column(name = "imagen")
    private String imagen; 

    // Precio unitario del producto.
    @Column(name = "precio_unitario", nullable = false)
    private Long precioUnitario;

    // Descripción del producto.
    @Column(name = "descripcion")
    private String descripcion;

    // Calificación del producto.
    @Column(name = "calificacion", nullable = false)
    private int calificacion;

    // Identificador de la categoría asociada al producto.
    @Column(name = "categorias_productos_id", nullable = false)
    private int idCategoriaProducto;

    // Estado del producto (activo/inactivo).
    @Column(name = "estado", nullable = true)
    private String estado;

    /**
     * Relación con la categoría del producto.
     * 
     * Resolver el problema de bucle infinito en la serialización de la relación CategoriaProducto-Producto.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "categorias_productos_id", insertable = false, updatable = false)
    private CategoriaProducto categoriaProducto;

//    // Identificadores adicionales relacionados con el producto.
//    @Column(name = "color_productos_id", nullable = false)
//    private int colorProductosId;
//
//    @Column(name = "oficio_id", nullable = false)
//    private int oficioId;
//
//    @Column(name = "coleccion_productos_id", nullable = false)
//    private int coleccionProductosId;
//
//    @Column(name = "artistas_productos_id", nullable = false)
//    private int artistasProductosId;

    /**
     * Relación con las facturas asociadas al producto.
     * 
     * Resolver el problema de bucle infinito en la serialización FacturaHasProducto-Producto.
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idProducto")
    private Set<FacturaHasProducto> facturasProducto;

    /**
     * Relación con los puntos de venta asociados al producto.
     * 
     * Resolver el problema de bucle infinito en la serialización ProductoHasPuntoVenta-Producto.
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idProducto")
    private Set<ProductoHasPuntoVenta> productoPuntosVentas;

    /**
     * Constructor vacío de la clase Producto.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
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

//    public int getColorProductosId() {
//        return colorProductosId;
//    }
//
//    public void setColorProductosId(int colorProductosId) {
//        this.colorProductosId = colorProductosId;
//    }
//
//    public int getOficioId() {
//        return oficioId;
//    }
//
//    public void setOficioId(int oficioId) {
//        this.oficioId = oficioId;
//    }
//
//    public int getColeccionProductosId() {
//        return coleccionProductosId;
//    }
//
//    public void setColeccionProductosId(int coleccionProductosId) {
//        this.coleccionProductosId = coleccionProductosId;
//    }
//
//    public int getArtistasProductosId() {
//        return artistasProductosId;
//    }
//
//    public void setArtistasProductosId(int artistasProductosId) {
//        this.artistasProductosId = artistasProductosId;
//    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}