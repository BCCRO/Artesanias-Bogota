package com.ud.report_module.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ud.report_module.models.dtos.FacturaHasProductoDTO;

import jakarta.persistence.*;

/**
 * Entidad que representa la relación entre una factura y un producto.
 * Contiene información sobre la cantidad de un producto asociado a una factura específica.
 */
@Entity
@Table(name = "facturas_has_productos", schema = "artesanias_bogota") // Especifica la tabla y el esquema en la base de datos.
public class FacturaHasProducto {

    // Identificador único de la relación.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Identificador de la factura asociada.
    @Column(name = "facturas_id", nullable = false)
    private Long idFactura;

    // Relación con la entidad Factura.
    @JsonIgnore // Evita la serialización de la factura para prevenir problemas de bucles infinitos.
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con la factura.
    @JoinColumn(name = "facturas_id", insertable = false, updatable = false)
    private Factura factura;

    // Identificador del producto asociado.
    @Column(name = "productos_id", nullable = false)
    private Long idProducto;

    // Relación con la entidad Producto.
    @ManyToOne(fetch = FetchType.LAZY) // Relación muchos a uno con el producto.
    @JoinColumn(name = "productos_id", insertable = false, updatable = false)
    private Producto producto;

    // Cantidad del producto asociada a la factura.
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    /**
     * Constructor por defecto.
     */
    public FacturaHasProducto() {
    }

    /**
     * Constructor que inicializa los datos a partir de un DTO.
     *
     * @param dto El DTO con los datos de la relación.
     */
    public FacturaHasProducto(FacturaHasProductoDTO dto) {
        this.idFactura = dto.getIdFactura();
        this.idProducto = dto.getIdProducto();
        this.cantidad = dto.getCantidad();
    }

    // Métodos getter y setter.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}