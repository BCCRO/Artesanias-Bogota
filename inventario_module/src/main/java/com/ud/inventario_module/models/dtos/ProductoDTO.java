package com.ud.inventario_module.models.dtos;

/**
 * DTO (Data Transfer Object) para manejar la información de un producto.
 * Contiene los datos necesarios para crear, actualizar y gestionar productos.
 */
public class ProductoDTO {

    // Nombre del producto.
    private String nombre;

    // Imagen asociada al producto.
    private String imagen;

    // Precio unitario del producto.
    private Long precioUnitario;

    // Descripción del producto.
    private String descripcion;

    // Calificación del producto, representada como un entero.
    private int calificacion;

    // ID de la categoría asociada al producto.
    private int idCategoriaProducto;

    // ID del color asociado al producto.
    private int colorProductosId;

    // ID del oficio asociado al producto.
    private int oficioId;

    // ID de la colección asociada al producto.
    private int coleccionProductosId;

    // ID del artista asociado al producto.
    private int artistasProductosId;

    /**
     * Constructor vacío de la clase ProductoDTO.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public ProductoDTO() {
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
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen asociada al producto.
     * 
     * @param imagen la imagen del producto a establecer.
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene el precio unitario del producto.
     * 
     * @return el precio unitario del producto.
     */
    public Long getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario del producto.
     * 
     * @param precioUnitario el precio unitario del producto a establecer.
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
     * Obtiene el ID de la categoría asociada al producto.
     * 
     * @return el ID de la categoría del producto.
     */
    public int getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    /**
     * Establece el ID de la categoría asociada al producto.
     * 
     * @param idCategoriaProducto el ID de la categoría del producto a establecer.
     */
    public void setIdCategoriaProducto(int idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }

    /**
     * Obtiene el ID del color asociado al producto.
     * 
     * @return el ID del color del producto.
     */
    public int getColorProductosId() {
        return colorProductosId;
    }

    /**
     * Establece el ID del color asociado al producto.
     * 
     * @param colorProductosId el ID del color del producto a establecer.
     */
    public void setColorProductosId(int colorProductosId) {
        this.colorProductosId = colorProductosId;
    }

    /**
     * Obtiene el ID del oficio asociado al producto.
     * 
     * @return el ID del oficio del producto.
     */
    public int getOficioId() {
        return oficioId;
    }

    /**
     * Establece el ID del oficio asociado al producto.
     * 
     * @param oficioId el ID del oficio del producto a establecer.
     */
    public void setOficioId(int oficioId) {
        this.oficioId = oficioId;
    }

    /**
     * Obtiene el ID de la colección asociada al producto.
     * 
     * @return el ID de la colección del producto.
     */
    public int getColeccionProductosId() {
        return coleccionProductosId;
    }

    /**
     * Establece el ID de la colección asociada al producto.
     * 
     * @param coleccionProductosId el ID de la colección del producto a establecer.
     */
    public void setColeccionProductosId(int coleccionProductosId) {
        this.coleccionProductosId = coleccionProductosId;
    }

    /**
     * Obtiene el ID del artista asociado al producto.
     * 
     * @return el ID del artista del producto.
     */
    public int getArtistasProductosId() {
        return artistasProductosId;
    }

    /**
     * Establece el ID del artista asociado al producto.
     * 
     * @param artistasProductosId el ID del artista del producto a establecer.
     */
    public void setArtistasProductosId(int artistasProductosId) {
        this.artistasProductosId = artistasProductosId;
    }
}