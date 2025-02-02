package com.ud.inventario_module.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "pedido", schema = "artesanias_bogota")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita errores de serialización relacionados con proxies de Hibernate.
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_factura", nullable = false)
    private Long idFactura;

    @Column(name = "id_puntos_venta")
    private Long idPuntosVenta;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "lat_entrega")
    private Double latEntrega;

    @Column(name = "long_entrega")
    private Double longEntrega;

    public Pedido() {
    }

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

    public Long getIdPuntosVenta() {
        return idPuntosVenta;
    }

    public void setIdPuntosVenta(Long idPuntosVenta) {
        this.idPuntosVenta = idPuntosVenta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getLatEntrega() {
        return latEntrega;
    }

    public void setLatEntrega(Double latEntrega) {
        this.latEntrega = latEntrega;
    }

    public Double getLongEntrega() {
        return longEntrega;
    }

    public void setLongEntrega(Double longEntrega) {
        this.longEntrega = longEntrega;
    }
}
