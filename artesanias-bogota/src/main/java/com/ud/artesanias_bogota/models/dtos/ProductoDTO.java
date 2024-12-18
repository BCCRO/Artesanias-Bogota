package com.ud.artesanias_bogota.models.dtos;

import jakarta.persistence.Column;

public class ProductoDTO {

    private String nombre;

    private String imagen;

    private Long precioUnitario; // TODO Validar estructura en la DB, si utilizamos mejor un bigDecimal

    private String descripcion;

    private int calificacion;

    private int idCategoriaProducto;

    private int colorProductosId;
    private int oficioId;
    private int coleccionProductosId;
    private int artistasProductosId;

    public ProductoDTO() {
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
