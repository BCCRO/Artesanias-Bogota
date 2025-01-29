package com.ud.inventario_module.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entidad que representa un usuario en el sistema.
 * Implementa la interfaz `UserDetails` para la integración con Spring Security.
 * Está mapeada a la tabla "usuarios" en el esquema "artesanias_bogota".
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios", schema = "artesanias_bogota")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Evita problemas de serialización con proxies de Hibernate.
public class Usuario implements UserDetails {

    // Documento único que identifica al usuario.
    @Id
    @Column(name = "documento", unique = true)
    private String documento;

    // Fecha de nacimiento del usuario.
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    // Teléfono único del usuario.
    @Column(name = "telefono", nullable = false, unique = true)
    private Long telefono;

    // Nombre y apellidos del usuario.
    @Column(name = "primer_nombre", nullable = false)
    private String primerNombre;

    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido", nullable = false)
    private String segundoApellido;

    // Fecha de creación del registro del usuario.
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    // Dirección física del usuario.
    @Column(name = "direccion", nullable = false)
    private String direccion;

    // Contraseña del usuario, necesaria para la autenticación.
    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    // Email único del usuario.
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Indica si el usuario está activo o inactivo.
    @Column(name = "activo", nullable = false)
    private boolean activo;

    // Relación uno a muchos con los roles asociados al usuario.
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
//    private Set<RolHasUsuario> rolesUsuario;


    @Column(name = "rol", nullable = false)
    private int idRol;

    //Dirección del usuario en formato de longitud y latitud
    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "latitud")
    private Double latitud;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "rol", insertable = false, updatable = false)
    private Rol rol;

    // Métodos de la interfaz UserDetails.

    /**
     * Obtiene los roles del usuario como una colección de autoridades para Spring Security.
     *
     * @return colección de roles como autoridades.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> collection = new ArrayList<>();

        collection.add(new SimpleGrantedAuthority(rol.getRol()));

        return collection;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return la contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return contrasenia;
    }

    /**
     * Obtiene el nombre de usuario (email) para la autenticación.
     *
     * @return el email del usuario.
     */
    @Override
    public String getUsername() {
        return email;
    }

    // Métodos adicionales.

    /**
     * Cambia el estado del usuario (activo/inactivo).
     */
    public void chageStatus() {
        this.activo = !this.activo;
    }
}