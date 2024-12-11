package com.ud.artesanias_bogota.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="categorias_puntos_venta", schema = "artesanias_bogota")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // TODO Provisional, resolver con el configurator https://stackoverflow.com/questions/52656517/no-serializer-found-for-class-org-hibernate-proxy-pojo-bytebuddy-bytebuddyinterc
public class CategoriaPuntoVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private Set<PuntoVenta> puntoVenta;

    public CategoriaPuntoVenta() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<PuntoVenta> getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(Set<PuntoVenta> puntoVenta) {
        this.puntoVenta = puntoVenta;
    }
}
