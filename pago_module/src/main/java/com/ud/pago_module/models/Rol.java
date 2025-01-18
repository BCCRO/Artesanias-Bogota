package com.ud.pago_module.models;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Entidad que representa un rol en el sistema.
 * Los roles se asignan a usuarios para gestionar permisos y funcionalidades.
 */
@Entity
@Table(name = "roles", schema = "artesanias_bogota") // Especifica la tabla y el esquema de la base de datos.
public class Rol {

    // Identificador único del rol.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Nombre del rol (ejemplo: ADMIN, USER).
    @Column(name = "rol", unique = true, nullable = false)
    private String rol;

    // Relación uno a muchos con la tabla que asocia usuarios con roles.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idRol") // Relación con RolHasUsuario.
    private Set<RolHasUsuario> usuariosRol;

    /**
     * Constructor por defecto.
     */
    public Rol() {
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador único del rol.
     *
     * @return El identificador del rol.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del rol.
     *
     * @param id El identificador del rol.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del rol.
     *
     * @return El nombre del rol.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el nombre del rol.
     *
     * @param rol El nombre del rol.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene la relación con los usuarios que tienen este rol.
     *
     * @return Un conjunto de asociaciones con usuarios.
     */
    public Set<RolHasUsuario> getUsuariosRol() {
        return usuariosRol;
    }

    /**
     * Establece la relación con los usuarios que tienen este rol.
     *
     * @param usuariosRol Un conjunto de asociaciones con usuarios.
     */
    public void setUsuariosRol(Set<RolHasUsuario> usuariosRol) {
        this.usuariosRol = usuariosRol;
    }
}