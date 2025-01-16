package com.ud.artesanias_bogota.models;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa un rol en el sistema.
 * Está mapeada a la tabla "roles" en el esquema "artesanias_bogota".
 */
@Entity
@Table(name = "roles", schema = "artesanias_bogota")
public class Rol {

    // Identificador único del rol.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del rol, debe ser único y no nulo.
    @Column(name = "rol", unique = true, nullable = false)
    private String rol;

    // Relación uno a muchos con la entidad RolHasUsuario.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idRol")
    private Set<RolHasUsuario> usuariosRol;

    /**
     * Constructor vacío de la clase Rol.
     * Es necesario para frameworks que requieren un constructor sin argumentos.
     */
    public Rol() {
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador único del rol.
     * 
     * @return el identificador del rol.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del rol.
     * 
     * @param id el identificador del rol a establecer.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del rol.
     * 
     * @return el nombre del rol.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el nombre del rol.
     * 
     * @param rol el nombre del rol a establecer.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el conjunto de relaciones entre este rol y los usuarios asociados.
     * 
     * @return un conjunto de relaciones entre rol y usuario.
     */
    public Set<RolHasUsuario> getUsuariosRol() {
        return usuariosRol;
    }

    /**
     * Establece el conjunto de relaciones entre este rol y los usuarios asociados.
     * 
     * @param usuariosRol el conjunto de relaciones a establecer.
     */
    public void setUsuariosRol(Set<RolHasUsuario> usuariosRol) {
        this.usuariosRol = usuariosRol;
    }
}