package com.ud.report_module.models.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PuntoVentaDTO {
    // Identificador único del punto de venta
    private Long id;
    
    // Nombre del punto de venta
    private String nombre;
    
    // Dirección del punto de venta
    private String direccion;
    
    // Ciudad donde se encuentra el punto de venta
    private String ciudad;
    
    // Departamento donde se encuentra el punto de venta
    private String departamento;
    
    // Identificador de la categoría del punto de venta
    private Integer categoriaPuntosVentaId;
    
    // Coordenada de longitud del punto de venta
    private Double longitud;
    
    // Coordenada de latitud del punto de venta
    private Double latitud;

    /**
     * Constructor vacío
     */
    public PuntoVentaDTO() {
    }

    /**
     * Constructor con parámetros
     * @param id Identificador del punto de venta
     * @param nombre Nombre del punto de venta
     * @param direccion Dirección del punto de venta
     * @param ciudad Ciudad del punto de venta
     * @param departamento Departamento del punto de venta
     * @param categoriaPuntosVentaId Identificador de la categoría del punto de venta
     * @param longitud Coordenada de longitud
     * @param latitud Coordenada de latitud
     */
    public PuntoVentaDTO(Long id, String nombre, String direccion, String ciudad, String departamento, Integer categoriaPuntosVentaId, Double longitud, Double latitud) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.categoriaPuntosVentaId = categoriaPuntosVentaId;
        this.longitud = longitud;
        this.latitud = latitud;
    }
}
