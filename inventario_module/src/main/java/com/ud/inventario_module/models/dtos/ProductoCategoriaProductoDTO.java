package com.ud.inventario_module.models.dtos;

import com.ud.inventario_module.models.CategoriaProducto;
import com.ud.inventario_module.models.Producto;

/**
 * DTO (Data Transfer Object) para manejar la información combinada de un producto
 * y su categoría asociada.
 * Este objeto facilita la transferencia de datos relacionados con productos y sus categorías.
 */
public class ProductoCategoriaProductoDTO {

    // Nombre del producto.
    private String nombre;

    // Imagen asociada al producto (puede ser un objeto para soportar diferentes tipos de datos).
    private Object imagen;

    // Precio unitario del producto.
    private Long precioUnitario;

    // Descripción del producto.
    private String descripcion;

    // Calificación del producto, representada como un entero.
    private int calificacion;

    // Categoría del producto asociada.
    private CategoriaProducto categoriaProducto;

    /**
     * Constructor vacío de la clase ProductoCategoriaProductoDTO.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public ProductoCategoriaProductoDTO() {
    }

    /**
     * Constructor que inicializa el DTO con los datos de un producto.
     * 
     * @param producto instancia de Producto cuyos datos se utilizarán para inicializar este DTO.
     */
    public ProductoCategoriaProductoDTO(Producto producto) {
        this.nombre = producto.getNombre();
        this.imagen = producto.getImagen();
        this.precioUnitario = producto.getPrecioUnitario();
        this.descripcion = producto.getDescripcion();
        this.calificacion = producto.getCalificacion();
        this.categoriaProducto = producto.getCategoriaProducto();
    }

    /**
     * Obtiene el nombre del producto.
     * 
     * @return el nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * 
     * @param nombre el nombre del producto a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la imagen asociada al producto.
     * 
     * @return la imagen del producto.
     */
    public Object getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen asociada al producto.
     * 
     * @param imagen la imagen del producto a establecer.
     */
    public void setImagen(Object imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el precio unitario del producto.
     * 
     * @return el precio unitario.
     */
    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario del producto.
     * 
     * @param precioUnitario el precio unitario a establecer.
     */
    public void setPrecioUnitario(Long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Obtiene la descripción del producto.
     * 
     * @return la descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     * 
     * @param descripcion la descripción del producto a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la calificación del producto.
     * 
     * @return la calificación del producto.
     */
    public int getCalificacion() {
        return calificacion;
    }

    /**
     * Establece la calificación del producto.
     * 
     * @param calificacion la calificación del producto a establecer.
     */
    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * Obtiene la categoría asociada al producto.
     * 
     * @return la categoría del producto.
     */
    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    /**
     * Establece la categoría asociada al producto.
     * 
     * @param categoriaProducto la categoría del producto a establecer.
     */
    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }
}