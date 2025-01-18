package com.ud.pago_module.models.dtos;

/**
 * DTO (Data Transfer Object) para representar los datos de un producto.
 * Se utiliza para transferir información entre diferentes capas de la aplicación.
 */
public class ProductoDTO {

    // Nombre del producto.
    private String nombre;

    // URL o representación de la imagen del producto.
    private String imagen;

    // Precio unitario del producto.
    private Long precioUnitario;

    // Descripción del producto.
    private String descripcion;

    // Calificación del producto (ejemplo: de 1 a 5 estrellas).
    private int calificacion;

    // ID de la categoría a la que pertenece el producto.
    private int idCategoriaProducto;

    // ID del color asociado al producto.
    private int colorProductosId;

    // ID del oficio asociado al producto.
    private int oficioId;

    // ID de la colección a la que pertenece el producto.
    private int coleccionProductosId;

    // ID del artista asociado al producto.
    private int artistasProductosId;

    /**
     * Constructor por defecto.
     */
    public ProductoDTO() {
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
     * Obtiene la imagen del producto.
     *
     * @return La imagen del producto.
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen del producto.
     *
     * @param imagen La imagen del producto.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el precio unitario del producto.
     *
     * @return El precio unitario del producto.
     */
    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario del producto.
     *
     * @param precioUnitario El precio unitario del producto.
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
     * Obtiene el ID de la categoría del producto.
     *
     * @return El ID de la categoría del producto.
     */
    public int getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    /**
     * Establece el ID de la categoría del producto.
     *
     * @param idCategoriaProducto El ID de la categoría del producto.
     */
    public void setIdCategoriaProducto(int idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }

    /**
     * Obtiene el ID del color asociado al producto.
     *
     * @return El ID del color del producto.
     */
    public int getColorProductosId() {
        return colorProductosId;
    }

    /**
     * Establece el ID del color asociado al producto.
     *
     * @param colorProductosId El ID del color del producto.
     */
    public void setColorProductosId(int colorProductosId) {
        this.colorProductosId = colorProductosId;
    }

    /**
     * Obtiene el ID del oficio asociado al producto.
     *
     * @return El ID del oficio del producto.
     */
    public int getOficioId() {
        return oficioId;
    }

    /**
     * Establece el ID del oficio asociado al producto.
     *
     * @param oficioId El ID del oficio del producto.
     */
    public void setOficioId(int oficioId) {
        this.oficioId = oficioId;
    }

    /**
     * Obtiene el ID de la colección del producto.
     *
     * @return El ID de la colección del producto.
     */
    public int getColeccionProductosId() {
        return coleccionProductosId;
    }

    /**
     * Establece el ID de la colección del producto.
     *
     * @param coleccionProductosId El ID de la colección del producto.
     */
    public void setColeccionProductosId(int coleccionProductosId) {
        this.coleccionProductosId = coleccionProductosId;
    }

    /**
     * Obtiene el ID del artista asociado al producto.
     *
     * @return El ID del artista del producto.
     */
    public int getArtistasProductosId() {
        return artistasProductosId;
    }

    /**
     * Establece el ID del artista asociado al producto.
     *
     * @param artistasProductosId El ID del artista del producto.
     */
    public void setArtistasProductosId(int artistasProductosId) {
        this.artistasProductosId = artistasProductosId;
    }
}
