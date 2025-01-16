package com.ud.artesanias_bogota.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa un punto de venta en el sistema.
 * Está mapeada a la tabla "puntos_venta" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "puntos_venta", schema = "artesanias_bogota")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita errores de serialización relacionados con proxies de Hibernate.
public class PuntoVenta {

    // Identificador único del punto de venta.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del punto de venta.
    @Column(name = "nombre", nullable = false)
    private String nombre;

    // Dirección física del punto de venta.
    @Column(name = "direccion", nullable = false)
    private String direccion;

    // Ciudad donde se ubica el punto de venta.
    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    // Departamento donde se ubica el punto de venta.
    @Column(name = "departamento", nullable = false)
    private String departamento;

    /**
     * Relación con los productos asociados al punto de venta.
     * 
     * Resolver el problema del bucle infinito en la serialización de la relación PuntoVenta-ProductoHasPuntoVenta.
     */
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idPuntoVenta")
    private Set<ProductoHasPuntoVenta> puntoVentaProductos;

    // Identificador de la categoría asociada al punto de venta.
    @Column(name = "categorias_puntos_venta_id", nullable = false)
    private String idCategoriaPuntoVenta;

    // Relación con la categoría del punto de venta.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorias_puntos_venta_id", insertable = false, updatable = false)
    private CategoriaPuntoVenta categoriaPuntoVenta;

    /**
     * Constructor vacío de la clase PuntoVenta.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public PuntoVenta() {
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador único del punto de venta.
     * 
     * @return el identificador del punto de venta.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del punto de venta.
     * 
     * @param id el identificador del punto de venta a establecer.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del punto de venta.
     * 
     * @return el nombre del punto de venta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del punto de venta.
     * 
     * @param nombre el nombre del punto de venta a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la dirección del punto de venta.
     * 
     * @return la dirección del punto de venta.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del punto de venta.
     * 
     * @param direccion la dirección del punto de venta a establecer.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la ciudad donde se encuentra el punto de venta.
     * 
     * @return la ciudad del punto de venta.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad donde se encuentra el punto de venta.
     * 
     * @param ciudad la ciudad del punto de venta a establecer.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene el departamento donde se encuentra el punto de venta.
     * 
     * @return el departamento del punto de venta.
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Establece el departamento donde se encuentra el punto de venta.
     * 
     * @param departamento el departamento del punto de venta a establecer.
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Obtiene el conjunto de productos asociados al punto de venta.
     * 
     * @return el conjunto de productos asociados.
     */
    public Set<ProductoHasPuntoVenta> getPuntoVentaProductos() {
        return puntoVentaProductos;
    }

    /**
     * Establece el conjunto de productos asociados al punto de venta.
     * 
     * @param puntoVentaProductos el conjunto de productos a asociar.
     */
    public void setPuntoVentaProductos(Set<ProductoHasPuntoVenta> puntoVentaProductos) {
        this.puntoVentaProductos = puntoVentaProductos;
    }

    /**
     * Obtiene el identificador de la categoría asociada al punto de venta.
     * 
     * @return el identificador de la categoría.
     */
    public String getIdCategoriaPuntoVenta() {
        return idCategoriaPuntoVenta;
    }

    /**
     * Establece el identificador de la categoría asociada al punto de venta.
     * 
     * @param idCategoriaPuntoVenta el identificador de la categoría a establecer.
     */
    public void setIdCategoriaPuntoVenta(String idCategoriaPuntoVenta) {
        this.idCategoriaPuntoVenta = idCategoriaPuntoVenta;
    }

    /**
     * Obtiene la categoría asociada al punto de venta.
     * 
     * @return la categoría del punto de venta.
     */
    public CategoriaPuntoVenta getCategoriaPuntoVenta() {
        return categoriaPuntoVenta;
    }

    /**
     * Establece la categoría asociada al punto de venta.
     * 
     * @param categoriaPuntoVenta la categoría del punto de venta a establecer.
     */
    public void setCategoriaPuntoVenta(CategoriaPuntoVenta categoriaPuntoVenta) {
        this.categoriaPuntoVenta = categoriaPuntoVenta;
    }
}