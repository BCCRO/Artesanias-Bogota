//package com.ud.artesanias_bogota.models;
//
//import jakarta.persistence.*;
//
///**
// * Entidad que representa la relación entre roles y usuarios en el sistema.
// * Está mapeada a la tabla "rol_has_usuario" en el esquema "artesanias_bogota".
// */
//@Entity
//@Table(name = "rol_has_usuario", schema = "artesanias_bogota")
//public class RolHasUsuario {
//
//    // Identificador único de la relación.
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // Documento del usuario asociado a la relación.
//    @Column(name = "documento_usuario", nullable = false)
//    private String documentoUsuario;
//
//    // Relación con la entidad Usuario.
//    @ManyToOne
//    @JoinColumn(name = "documento_usuario", insertable = false, updatable = false)
//    private Usuario usuario;
//
//    // Identificador del rol asociado a la relación.
//    @Column(name = "id_rol", nullable = false)
//    private Long idRol;
//
//    // Relación con la entidad Rol.
//    @ManyToOne
//    @JoinColumn(name = "id_rol", insertable = false, updatable = false)
//    private Rol rol;
//
//    /**
//     * Constructor vacío de la clase RolHasUsuario.
//     * Es necesario para frameworks que requieren un constructor sin argumentos.
//     */
//    public RolHasUsuario() {
//    }
//
//    /**
//     * Constructor que inicializa la relación con las entidades Usuario y Rol.
//     *
//     * @param usuario el usuario asociado a la relación.
//     * @param rol el rol asociado a la relación.
//     */
//    public RolHasUsuario(Usuario usuario, Rol rol) {
//        this.usuario = usuario;
//        this.rol = rol;
//        this.documentoUsuario = usuario.getDocumento();
//        this.idRol = rol.getId();
//    }
//
//    // Métodos getter y setter.
//
//    /**
//     * Obtiene el identificador único de la relación.
//     *
//     * @return el identificador de la relación.
//     */
//    public Long getId() {
//        return id;
//    }
//
//    /**
//     * Establece el identificador único de la relación.
//     *
//     * @param id el identificador de la relación a establecer.
//     */
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    /**
//     * Obtiene el documento del usuario asociado a la relación.
//     *
//     * @return el documento del usuario.
//     */
//    public String getDocumentoUsuario() {
//        return documentoUsuario;
//    }
//
//    /**
//     * Establece el documento del usuario asociado a la relación.
//     *
//     * @param documentoUsuario el documento del usuario a establecer.
//     */
//    public void setDocumentoUsuario(String documentoUsuario) {
//        this.documentoUsuario = documentoUsuario;
//    }
//
//    /**
//     * Obtiene la entidad Usuario asociada a la relación.
//     *
//     * @return el usuario asociado.
//     */
//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    /**
//     * Establece la entidad Usuario asociada a la relación.
//     *
//     * @param usuario el usuario a asociar.
//     */
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }
//
//    /**
//     * Obtiene el identificador del rol asociado a la relación.
//     *
//     * @return el identificador del rol.
//     */
//    public Long getIdRol() {
//        return idRol;
//    }
//
//    /**
//     * Establece el identificador del rol asociado a la relación.
//     *
//     * @param idRol el identificador del rol a establecer.
//     */
//    public void setIdRol(Long idRol) {
//        this.idRol = idRol;
//    }
//
//    /**
//     * Obtiene la entidad Rol asociada a la relación.
//     *
//     * @return el rol asociado.
//     */
//    public Rol getRol() {
//        return rol;
//    }
//
//    /**
//     * Establece la entidad Rol asociada a la relación.
//     *
//     * @param rol el rol a asociar.
//     */
//    public void setRol(Rol rol) {
//        this.rol = rol;
//    }
//}