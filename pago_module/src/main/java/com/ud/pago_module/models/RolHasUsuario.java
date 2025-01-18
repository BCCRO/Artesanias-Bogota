package com.ud.pago_module.models;

import jakarta.persistence.*;

/**
 * Entidad que representa la relación entre un usuario y un rol.
 * Cada instancia de esta clase vincula un usuario con un rol específico.
 */
@Entity
@Table(name = "rol_has_usuario", schema = "artesanias_bogota") // Especifica la tabla y el esquema en la base de datos.
public class RolHasUsuario {

    // Identificador único de la relación.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia de generación automática para la clave primaria.
    private Long id;

    // Documento del usuario asociado.
    @Column(name = "documento_usuario", nullable = false)
    private String documentoUsuario;

    // Relación con la entidad Usuario.
    @ManyToOne // Relación muchos a uno con Usuario.
    @JoinColumn(name = "documento_usuario", insertable = false, updatable = false) // Mapeo con la columna 'documento_usuario'.
    private Usuario usuario;

    // Identificador del rol asociado.
    @Column(name = "id_rol", nullable = false)
    private Long idRol;

    // Relación con la entidad Rol.
    @ManyToOne // Relación muchos a uno con Rol.
    @JoinColumn(name = "id_rol", insertable = false, updatable = false) // Mapeo con la columna 'id_rol'.
    private Rol rol;

    /**
     * Constructor por defecto.
     */
    public RolHasUsuario() {
    }

    /**
     * Constructor que inicializa la relación entre un usuario y un rol.
     *
     * @param usuario El usuario asociado.
     * @param rol     El rol asociado.
     */
    public RolHasUsuario(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
        this.documentoUsuario = usuario.getDocumento();
        this.idRol = rol.getId();
    }

    // Métodos getter y setter.

    /**
     * Obtiene el identificador único de la relación.
     *
     * @return El identificador de la relación.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la relación.
     *
     * @param id El identificador de la relación.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el documento del usuario asociado.
     *
     * @return El documento del usuario.
     */
    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    /**
     * Establece el documento del usuario asociado.
     *
     * @param documentoUsuario El documento del usuario.
     */
    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    /**
     * Obtiene el usuario asociado.
     *
     * @return El usuario asociado.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario asociado.
     *
     * @param usuario El usuario asociado.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el identificador del rol asociado.
     *
     * @return El identificador del rol.
     */
    public Long getIdRol() {
        return idRol;
    }

    /**
     * Establece el identificador del rol asociado.
     *
     * @param idRol El identificador del rol.
     */
    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    /**
     * Obtiene el rol asociado.
     *
     * @return El rol asociado.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol asociado.
     *
     * @param rol El rol asociado.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}