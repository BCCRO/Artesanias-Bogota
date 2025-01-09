package com.ud.pago_module.models.dtos;

import java.util.HashSet;

import com.ud.pago_module.models.CategoriaProducto;
import com.ud.pago_module.models.Producto;

public class ProductoCategoriaProductoDTO {

    private String nombre;

    private Object imagen;  //TODO validar tipo de objeto, creo que se guarda en base64

    private Long precioUnitario; // TODO Validar estructura en la DB, si utilizamos mejor un bigDecimal

    private String descripcion;

    private int calificacion;

    private CategoriaProducto categoriaProducto;

    public ProductoCategoriaProductoDTO() {
    }

    public ProductoCategoriaProductoDTO(Producto producto) {
        this.nombre = producto.getNombre();
        this.imagen = producto.getImagen();
        this.precioUnitario = producto.getPrecioUnitario();
        this.descripcion = producto.getDescripcion();
        this.calificacion = producto.getCalificacion();
        this.categoriaProducto = producto.getCategoriaProducto();
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

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }
}
