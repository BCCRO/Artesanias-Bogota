package com.ud.artesanias_bogota.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "productos ")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "imagen", nullable = false)
    private Object imagen;  //TODO validar tipo de objeto, creo que se guarda en base64

    @Column(name = "precio_unitario", nullable = false)
    private Long precioUnitario; // TODO Validar estructura en la DB, si utilizamos mejor un bigDecimal

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "calificacion", nullable = false)
    private int calificacion;

    @Column(name = "categorias_productos_id", nullable = false)
    private int idCategoriaProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorias_productos_id", insertable = false, updatable = false)
    private CategoriaProducto categoriaProducto;

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

    public Object getImagen() {
        return imagen;
    }

    public void setImagen(Object imagen) {
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
