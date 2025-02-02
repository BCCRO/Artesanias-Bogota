package com.ud.report_module.models.dtos;

import lombok.Builder;
import lombok.Data;

/**
 * DTO (Data Transfer Object) para representar los datos de un producto.
 * Se utiliza para transferir información entre diferentes capas de la aplicación.
 */

@Data
@Builder
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
}