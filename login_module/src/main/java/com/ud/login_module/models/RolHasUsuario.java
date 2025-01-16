package com.ud.login_module.models;

import jakarta.persistence.*;

@Entity
@Table(name="rol_has_usuario", schema = "artesanias_bogota")
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
    private Long idRol;

    @ManyToOne
    @JoinColumn(name="id_rol", insertable = false, updatable = false)
    private Rol rol;

    public RolHasUsuario(){
      
    }

    public RolHasUsuario(Usuario usuario, Rol rol) {
      this.usuario = usuario;
      this.rol = rol;
      this.documentoUsuario = usuario.getDocumento();
      this.idRol= rol.getId();
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

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
