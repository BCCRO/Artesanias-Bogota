package com.ud.login_module.User;

import jakarta.persistence.*;

/**
 * Clase que representa la relación entre un usuario y un rol en la base de datos.
 * Mapea la tabla "rol_has_usuario" en el esquema "artesanias_bogota".
 */
@Entity // Indica que esta clase es una entidad JPA.
@Table(name = "rol_has_usuario", schema = "artesanias_bogota") // Especifica el nombre de la tabla y el esquema.
public class RolHasUsuario {

    // Identificador único de la relación.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID es autogenerado.
    private Long id;

    // Documento del usuario asociado al rol.
    @Column(name = "documento_usuario", nullable = false) // Especifica que este campo es obligatorio.
    private String documentoUsuario;

    // Relación con la entidad Usuario, basada en "documento_usuario".
    @ManyToOne // Define una relación muchos a uno con Usuario.
    @JoinColumn(name = "documento_usuario", insertable = false, updatable = false) // Mapea la columna "documento_usuario".
    private Usuario usuario;

    // Identificador del rol asociado al usuario.
    @Column(name = "id_rol", nullable = false) // Especifica que este campo es obligatorio.
    private Long idRol;

    // Relación con la entidad Rol, basada en "id_rol".
    @ManyToOne // Define una relación muchos a uno con Rol.
    @JoinColumn(name = "id_rol", insertable = false, updatable = false) // Mapea la columna "id_rol".
    private Rol rol;

    /**
     * Constructor vacío necesario para JPA.
     */
    public RolHasUsuario() {
    }

    /**
     * Constructor para inicializar la relación entre usuario y rol.
     *
     * @param usuario El usuario asociado.
     * @param rol El rol asociado.
     */
    public RolHasUsuario(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
        this.documentoUsuario = usuario.getDocumento();
        this.idRol = rol.getId();
    }

    /**
     * Obtiene el ID de la relación.
     *
     * @return El ID de la relación.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el ID de la relación.
     *
     * @param id El ID de la relación.
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
     * Obtiene el usuario asociado a la relación.
     *
     * @return El usuario asociado.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario asociado a la relación.
     *
     * @param usuario El usuario a asociar.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el ID del rol asociado.
     *
     * @return El ID del rol.
     */
    public Long getIdRol() {
        return idRol;
    }

    /**
     * Establece el ID del rol asociado.
     *
     * @param idRol El ID del rol.
     */
    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    /**
     * Obtiene el rol asociado a la relación.
     *
     * @return El rol asociado.
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol asociado a la relación.
     *
     * @param rol El rol a asociar.
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
}