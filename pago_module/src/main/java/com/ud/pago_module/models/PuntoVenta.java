package com.ud.pago_module.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa un punto de venta en el sistema.
 * Un punto de venta está asociado a una categoría y puede contener múltiples productos.
 */
@Entity
@Table(name = "puntos_venta", schema = "artesanias_bogota") // Especifica la tabla y el esquema en la base de datos.
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita errores relacionados con Hibernate.
public class PuntoVenta {

    // Identificador único del punto de venta.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Nombre del punto de venta.
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Dirección del punto de venta.
    @Column(name = "direccion", nullable = false)
    private String direccion;

    // Ciudad donde se encuentra el punto de venta.
    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    // Departamento donde se encuentra el punto de venta.
    @Column(name = "departamento", nullable = false)
    private String departamento;

    // Relación uno a muchos con productos disponibles en este punto de venta.
    @JsonIgnore // Evita la serialización para prevenir problemas de bucles infinitos.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idPuntoVenta")
    private Set<ProductoHasPuntoVenta> puntoVentaProductos;

    //Dirección del punto de venta en formato de longitud y latitud
    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "latitud")
    private Double latitud;

    // Identificador de la categoría del punto de venta.
    @Column(name = "categorias_puntos_venta_id", nullable = false)
    private String idCategoriaPuntoVenta;

    // Relación muchos a uno con la categoría del punto de venta.
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con la categoría del punto de venta.
    @JoinColumn(name = "categorias_puntos_venta_id", insertable = false, updatable = false) // Mapeo con la columna 'categorias_puntos_venta_id'.
    private CategoriaPuntoVenta categoriaPuntoVenta;

    /**
     * Constructor por defecto.
     */
    public PuntoVenta() {
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador del punto de venta.
     *
     * @return El identificador del punto de venta.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del punto de venta.
     *
     * @param id El identificador del punto de venta.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del punto de venta.
     *
     * @return El nombre del punto de venta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del punto de venta.
     *
     * @param nombre El nombre del punto de venta.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la dirección del punto de venta.
     *
     * @return La dirección del punto de venta.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del punto de venta.
     *
     * @param direccion La dirección del punto de venta.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la ciudad donde se encuentra el punto de venta.
     *
     * @return La ciudad del punto de venta.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad donde se encuentra el punto de venta.
     *
     * @param ciudad La ciudad del punto de venta.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene el departamento donde se encuentra el punto de venta.
     *
     * @return El departamento del punto de venta.
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Establece el departamento donde se encuentra el punto de venta.
     *
     * @param departamento El departamento del punto de venta.
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Obtiene la relación con los productos disponibles en este punto de venta.
     *
     * @return Un conjunto de relaciones producto-punto de venta.
     */
    public Set<ProductoHasPuntoVenta> getPuntoVentaProductos() {
        return puntoVentaProductos;
    }

    /**
     * Establece la relación con los productos disponibles en este punto de venta.
     *
     * @param puntoVentaProductos Un conjunto de relaciones producto-punto de venta.
     */
    public void setPuntoVentaProductos(Set<ProductoHasPuntoVenta> puntoVentaProductos) {
        this.puntoVentaProductos = puntoVentaProductos;
    }

    /**
     * Obtiene el identificador de la categoría del punto de venta.
     *
     * @return El identificador de la categoría.
     */
    public String getIdCategoriaPuntoVenta() {
        return idCategoriaPuntoVenta;
    }

    /**
     * Establece el identificador de la categoría del punto de venta.
     *
     * @param idCategoriaPuntoVenta El identificador de la categoría.
     */
    public void setIdCategoriaPuntoVenta(String idCategoriaPuntoVenta) {
        this.idCategoriaPuntoVenta = idCategoriaPuntoVenta;
    }

    /**
     * Obtiene la categoría del punto de venta.
     *
     * @return La categoría del punto de venta.
     */
    public CategoriaPuntoVenta getCategoriaPuntoVenta() {
        return categoriaPuntoVenta;
    }

    /**
     * Establece la categoría del punto de venta.
     *
     * @param categoriaPuntoVenta La categoría del punto de venta.
     */
    public void setCategoriaPuntoVenta(CategoriaPuntoVenta categoriaPuntoVenta) {
        this.categoriaPuntoVenta = categoriaPuntoVenta;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    } 
}