package com.ud.login_module.User;

import jakarta.persistence.*;
import java.util.Set;

/**
 * Clase que representa la entidad Rol en la base de datos.
 * Contiene la información sobre los roles disponibles en el sistema.
 */
@Entity // Indica que esta clase es una entidad JPA.
@Table(name = "roles", schema = "artesanias_bogota") // Especifica el nombre de la tabla y el esquema en la base de datos.
public class Rol {

    // Identificador único del rol.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID es autogenerado.
    private Long id;

    // Nombre del rol (debe ser único y no nulo).
    @Column(name = "rol", unique = true, nullable = false)
    private String rol;

    // Relación uno a muchos con la tabla RolHasUsuario.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documento")
    private Set<Usuario> usuarios;

    /**
     * Constructor vacío necesario para JPA.
     */
    public Rol() {
    }

    /**
     * Obtiene el ID del rol.
     *
     * @return El ID del rol.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID del rol.
     *
     * @param id El ID del rol.
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
     * Obtiene los usuarios asociados a este rol.
     *
     * @return Un conjunto de relaciones con la entidad RolHasUsuario.
     */
    public Set<Usuario> getUsuariosRol() {
        return usuarios;
    }

    /**
     * Establece los usuarios asociados a este rol.
     *
     * @param usuariosRol Un conjunto de relaciones con la entidad RolHasUsuario.
     */
    public void setUsuariosRol(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}