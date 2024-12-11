package com.login_module.login_module.User;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="roles", schema = "artesanias_bogota")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="rol", unique = true, nullable = false)
    private String rol;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idRol")
    private Set<RolHasUsuario> usuariosRol;

    public Rol() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Set<RolHasUsuario> getUsuariosRol() {
        return usuariosRol;
    }

    public void setUsuariosRol(Set<RolHasUsuario> usuariosRol) {
        this.usuariosRol = usuariosRol;
    }
}
