package com.ud.inventario_module.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ud.inventario_module.models.dtos.FacturaHasProductoDTO;
import jakarta.persistence.*;

/**
 * Entidad que representa la relación entre facturas y productos en el sistema.
 * Está mapeada a la tabla "facturas_has_productos" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "facturas_has_productos", schema = "artesanias_bogota")
public class FacturaHasProducto {

    // Identificador único de la relación.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Identificador de la factura asociada.
    @Column(name = "facturas_id", nullable = false)
    private Long idFactura;

    /**
     * Relación con la entidad Factura.
     * Utiliza LAZY fetch para evitar la carga automática de la entidad completa.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facturas_id", insertable = false, updatable = false)
    private Factura factura;

    // Identificador del producto asociado.
    @Column(name = "productos_id", nullable = false)
    private Long idProducto;

    // Relación con la entidad Producto.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productos_id", insertable = false, updatable = false)
    private Producto producto;

    // Cantidad del producto en la factura.
    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    /**
     * Identificador del punto de venta asociado.
     * Nota: Se omite la relación @ManyToOne para evitar bucles en la serialización.
     */
    @Column(name = "punto_venta_id", nullable = false)
    private Long idPuntoVenta;

    /**
     * Constructor vacío necesario para frameworks.
     */
    public FacturaHasProducto() {
    }

    /**
     * Constructor que inicializa la entidad a partir de un DTO.
     * 
     * @param dto Objeto FacturaHasProductoDTO con los datos necesarios.
     */
    public FacturaHasProducto(FacturaHasProductoDTO dto) {
        this.idFactura = dto.getIdFactura();
        this.idProducto = dto.getIdProducto();
        this.cantidad = dto.getCantidad();
        this.idPuntoVenta = dto.getIdPuntoVenta();
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

    public Long getIdPuntoVenta() {
        return idPuntoVenta;
    }

    public void setIdPuntoVenta(Long idPuntoVenta) {
        this.idPuntoVenta = idPuntoVenta;
    }
}