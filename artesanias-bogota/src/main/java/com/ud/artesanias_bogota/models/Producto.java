package com.ud.artesanias_bogota.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "productos", schema = "artesanias_bogota")
//@NamedEntityGraph(
//        name = "producto.categoriaProducto",
//        attributeNodes = @NamedAttributeNode("categoriaProducto")
//)
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Lob
    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

    @Column(name = "precio_unitario", nullable = false)
    private Long precioUnitario; // TODO Validar estructura en la DB, si utilizamos mejor un bigDecimal

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "calificacion", nullable = false)
    private int calificacion;

    @Column(name = "categorias_productos_id", nullable = false)
    private int idCategoriaProducto;

    /**
     * TODO
     * Tenemos un error en donde se crea un bucle infinito entre la relacion CategoriaProducto - Producto
     * Deberia solucionarse con el fetch LAZY, pero no es asi
     * forzamos esto para evitar el bucle en la respuesta, pero no podriamos cargar las CategoriaProducto automaticamente desde el producto cuando lo necesitemos
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "categorias_productos_id", insertable = false, updatable = false)
    @JsonIgnore
    private CategoriaProducto categoriaProducto;

    /**
     * TODO relacionar los siguiente parametros
     */
    @Column(name = "color_productos_id", nullable = false)
    private int colorProductosId;
    @Column(name = "oficio_id", nullable = false)
    private int oficioId;
    @Column(name = "coleccion_productos_id", nullable = false)
    private int coleccionProductosId;
    @Column(name = "artistas_productos_id", nullable = false)
    private int artistasProductosId;
    /**
     *
     */

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idProducto")
    private Set<FacturaHasProducto> facturasProducto;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idProducto")
    private Set<ProductoHasPuntoVenta> productoPuntosVentas;

    public Producto() {
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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
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

    /**
     *
     */
    public int getColorProductosId() {
        return colorProductosId;
    }

    public void setColorProductosId(int colorProductosId) {
        this.colorProductosId = colorProductosId;
    }

    public int getOficioId() {
        return oficioId;
    }

    public void setOficioId(int oficioId) {
        this.oficioId = oficioId;
    }

    public int getColeccionProductosId() {
        return coleccionProductosId;
    }

    public void setColeccionProductosId(int coleccionProductosId) {
        this.coleccionProductosId = coleccionProductosId;
    }

    public int getArtistasProductosId() {
        return artistasProductosId;
    }

    public void setArtistasProductosId(int artistasProductosId) {
        this.artistasProductosId = artistasProductosId;
    }
}