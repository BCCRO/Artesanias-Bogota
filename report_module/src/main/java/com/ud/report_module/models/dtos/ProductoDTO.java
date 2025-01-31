package com.ud.report_module.models.dtos;

/**
 * DTO (Data Transfer Object) para representar los datos de un producto.
 * Se utiliza para transferir información entre diferentes capas de la aplicación.
 */
public class ProductoDTO {

    // Identificador único del producto
    private Long id;

    // Nombre del producto
    private String nombre;

    // URL o representación de la imagen del producto
    private String imagen;

    // Precio unitario del producto
    private Long precioUnitario;

    // Descripción del producto
    private String descripcion;

    // Calificación del producto (ejemplo: de 1 a 5 estrellas)
    private int calificacion;

    // ID de la categoría a la que pertenece el producto
    private int idCategoriaProducto;

    /**
     * Constructor vacío
     */
    public ProductoDTO() {
    }

    /**
     * Constructor con parámetros
     * 
     * @param id Identificador único del producto
     * @param nombre Nombre del producto
     * @param imagen Imagen del producto
     * @param precioUnitario Precio unitario del producto
     * @param descripcion Descripción del producto
     * @param calificacion Calificación del producto
     * @param idCategoriaProducto ID de la categoría del producto
     */
    public ProductoDTO(Long id, String nombre, String imagen, Long precioUnitario, String descripcion, int calificacion, int idCategoriaProducto) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.precioUnitario = precioUnitario;
        this.descripcion = descripcion;
        this.calificacion = calificacion;
        this.idCategoriaProducto = idCategoriaProducto;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}