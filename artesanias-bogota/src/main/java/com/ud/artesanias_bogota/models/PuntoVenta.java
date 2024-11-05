package com.ud.artesanias_bogota.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="puntos_venta", schema = "artesanias_bogota")
public class PuntoVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="direccion", nullable = false)
    private String direccion;

    @Column(name="ciudad", nullable = false)
    private String ciudad;

    @Column(name="departamento", nullable = false)
    private String departamento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idPuntoVenta")
    private Set<ProductoHasPuntoVenta> puntoVentaProductos;

    @Column(name="categorias_puntos_venta_id", nullable = false)
    private String idCategoriaPuntoVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categorias_puntos_venta_id", insertable = false, updatable = false)
    private CategoriaPuntoVenta categoriaPuntoVenta;

    public PuntoVenta() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Set<ProductoHasPuntoVenta> getPuntoVentaProductos() {
        return puntoVentaProductos;
    }

    public void setPuntoVentaProductos(Set<ProductoHasPuntoVenta> puntoVentaProductos) {
        this.puntoVentaProductos = puntoVentaProductos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCategoriaPuntoVenta() {
        return idCategoriaPuntoVenta;
    }

    public void setIdCategoriaPuntoVenta(String idCategoriaPuntoVenta) {
        this.idCategoriaPuntoVenta = idCategoriaPuntoVenta;
    }

    public CategoriaPuntoVenta getCategoriaPuntoVenta() {
        return categoriaPuntoVenta;
    }

    public void setCategoriaPuntoVenta(CategoriaPuntoVenta categoriaPuntoVenta) {
        this.categoriaPuntoVenta = categoriaPuntoVenta;
    }
}
