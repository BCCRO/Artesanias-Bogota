package com.ud.pago_module.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entidad que representa a los usuarios en el sistema.
 * Implementa la interfaz {@link UserDetails} para integrarse con Spring Security.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios", schema = "artesanias_bogota")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})  // Evita problemas con serialización de proxies.
public class Usuario implements UserDetails {

    // Identificador único del usuario (documento).
    @Id
    @Column(name = "documento", unique = true)
    private String documento;

    // Fecha de nacimiento del usuario.
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    // Teléfono del usuario (debe ser único).
    @Column(name = "telefono", nullable = false, unique = true)
    private Long telefono;

    // Primer nombre del usuario.
    @Column(name = "primer_nombre", nullable = false)
    private String primerNombre;

    // Segundo nombre del usuario (opcional).
    @Column(name = "segundo_nombre")
    private String segundoNombre;

    // Primer apellido del usuario.
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    // Segundo apellido del usuario.
    @Column(name = "segundo_apellido", nullable = false)
    private String segundoApellido;

    // Fecha en que se creó el registro del usuario.
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    // Dirección del usuario.
    @Column(name = "direccion", nullable = false)
    private String direccion;

    // Contraseña del usuario.
    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    // Correo electrónico del usuario (debe ser único).
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Estado activo/inactivo del usuario.
    @Column(name = "activo", nullable = false)
    private boolean activo;

    //Dirección del usuario en formato de longitud y latitud
    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "latitud")
    private Double latitud;

    // Relación uno a muchos con los roles asignados al usuario.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
    private Set<RolHasUsuario> rolesUsuario;

    /**
     * Obtiene los roles del usuario como una colección de autoridades.
     *
     * @return Colección de roles del usuario.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesUsuario.stream()
            .map(rol -> new SimpleGrantedAuthority(rol.getRol().getRol()))
            .toList();
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return getContrasenia();
    }

    /**
     * Obtiene el nombre de usuario (correo electrónico) para la autenticación.
     *
     * @return Correo electrónico del usuario.
     */
    @Override
    public String getUsername() {
        return getEmail();
    }

    /**
     * Cambia el estado activo/inactivo del usuario.
     */
    public void chageStatus() {
        this.activo = !this.activo;
    }
}