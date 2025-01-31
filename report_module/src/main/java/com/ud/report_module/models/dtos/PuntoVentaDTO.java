package com.ud.report_module.models.dtos;

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
    public PuntoVentaDTO(Long id, String nombre, String direccion, String ciudad, String departamento, 
                         Integer categoriaPuntosVentaId, Double longitud, Double latitud) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.categoriaPuntosVentaId = categoriaPuntosVentaId;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    /**
     * Obtiene el identificador del punto de venta
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del punto de venta
     * @param id Nuevo identificador
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del punto de venta
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del punto de venta
     * @param nombre Nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la dirección del punto de venta
     * @return direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del punto de venta
     * @param direccion Nueva dirección
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la ciudad del punto de venta
     * @return ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad del punto de venta
     * @param ciudad Nueva ciudad
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene el departamento del punto de venta
     * @return departamento
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Establece el departamento del punto de venta
     * @param departamento Nuevo departamento
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Obtiene el identificador de la categoría del punto de venta
     * @return categoriaPuntosVentaId
     */
    public Integer getCategoriaPuntosVentaId() {
        return categoriaPuntosVentaId;
    }

    /**
     * Establece el identificador de la categoría del punto de venta
     * @param categoriaPuntosVentaId Nuevo identificador de categoría
     */
    public void setCategoriaPuntosVentaId(Integer categoriaPuntosVentaId) {
        this.categoriaPuntosVentaId = categoriaPuntosVentaId;
    }

    /**
     * Obtiene la coordenada de longitud del punto de venta
     * @return longitud
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * Establece la coordenada de longitud del punto de venta
     * @param longitud Nueva coordenada de longitud
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    /**
     * Obtiene la coordenada de latitud del punto de venta
     * @return latitud
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * Establece la coordenada de latitud del punto de venta
     * @param latitud Nueva coordenada de latitud
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
}
