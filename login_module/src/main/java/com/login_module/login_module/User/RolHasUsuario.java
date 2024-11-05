package com.login_module.login_module.User;

import jakarta.persistence.*;

@Entity
@Table(name="rol_has_usuario")
public class RolHasUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="documento_usuario", nullable = false)
    private String documentoUsuario;

    @ManyToOne
    @JoinColumn(name="documento_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name="id_rol", nullable = false)
    private int idRol;

    @ManyToOne
    @JoinColumn(name="id_rol", insertable = false, updatable = false)
    private Rol rol;

    public RolHasUsuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}