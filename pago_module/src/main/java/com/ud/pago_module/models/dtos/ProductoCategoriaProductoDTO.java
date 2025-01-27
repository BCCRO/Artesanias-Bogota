package com.ud.pago_module.models.dtos;

import com.ud.pago_module.models.CategoriaProducto;
import com.ud.pago_module.models.Producto;

/**
 * DTO (Data Transfer Object) que combina información de un producto y su categoría.
 * Se utiliza para transferir datos entre capas de la aplicación.
 */
public class ProductoCategoriaProductoDTO {

    // Nombre del producto.
    private String nombre;

    // Imagen asociada al producto.
    private Object imagen;

    // Precio unitario del producto.
    private Long precioUnitario;

    // Descripción del producto.
    private String descripcion;

    // Calificación del producto (ej. de 1 a 5 estrellas).
    private int calificacion;

    // Categoría asociada al producto.
    private CategoriaProducto categoriaProducto;

    /**
     * Constructor por defecto.
     */
    public ProductoCategoriaProductoDTO() {
    }

    /**
     * Constructor que inicializa los datos del DTO a partir de un objeto Producto.
     *
     * @param producto El producto del cual se extraerán los datos.
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
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre El nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la imagen asociada al producto.
     *
     * @return La imagen del producto.
     */
    public Object getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen asociada al producto.
     *
     * @param imagen La imagen del producto.
     */
    public void setImagen(Object imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el precio unitario del producto.
     *
     * @return El precio unitario.
     */
    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precioUnitario El precio unitario.
     */
    public void setPrecioUnitario(Long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Obtiene la descripción del producto.
     *
     * @return La descripción del producto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     *
     * @param descripcion La descripción del producto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la calificación del producto.
     *
     * @return La calificación del producto.
     */
    public int getCalificacion() {
        return calificacion;
    }

    /**
     * Establece la calificación del producto.
     *
     * @param calificacion La calificación del producto.
     */
    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    /**
     * Obtiene la categoría asociada al producto.
     *
     * @return La categoría del producto.
     */
    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    /**
     * Establece la categoría asociada al producto.
     *
     * @param categoriaProducto La categoría del producto.
     */
    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }
}
