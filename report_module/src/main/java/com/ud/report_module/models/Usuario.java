package com.ud.report_module.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * Clase que representa la entidad Usuario.
 * Implementa la interfaz UserDetails para integrarse con Spring Security.
 */
@Data // Genera automáticamente métodos getter, setter, equals, hashCode y toString.
@Builder // Permite construir instancias de la clase utilizando el patrón Builder.
@AllArgsConstructor // Genera un constructor con todos los atributos.
@NoArgsConstructor // Genera un constructor sin parámetros.
@Entity // Indica que esta clase es una entidad JPA.
@Table(name = "usuarios", schema = "artesanias_bogota") // Especifica el nombre de la tabla y el esquema en la base de datos.
public class Usuario implements UserDetails {

    // Documento único que identifica al usuario.
    @Id
    @Column(name = "documento", unique = true)
    private String documento;

    // Fecha de nacimiento del usuario.
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    // Número de teléfono único del usuario.
    @Column(name = "telefono", nullable = false, unique = true)
    private Long telefono;

    // Primer nombre del usuario.
    @Column(name = "primer_nombre", nullable = false)
    private String primerNombre;

    // Segundo nombre del usuario.
    @Column(name = "segundo_nombre")
    private String segundoNombre;

    // Primer apellido del usuario.
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    // Segundo apellido del usuario.
    @Column(name = "segundo_apellido", nullable = false)
    private String segundoApellido;

    // Fecha de creación del usuario en el sistema.
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    // Dirección del usuario.
    @Column(name = "direccion", nullable = false)
    private String direccion;

    // Contraseña del usuario.
    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    // Correo electrónico único del usuario.
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Estado de actividad del usuario.
    @Column(name = "activo", nullable = false)
    private boolean activo;

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

    /**
     * Obtiene las autoridades (roles) del usuario.
     *
     * @return Una colección de GrantedAuthority que representa los roles del usuario.
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
     * @return La contraseña del usuario.
     */
    @Override
    public String getPassword() {
        return contrasenia;
    }

    /**
     * Obtiene el nombre de usuario (correo electrónico).
     *
     * @return El correo electrónico del usuario.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Cambia el estado de actividad del usuario.
     */
    public void chageStatus() {
        this.activo = !this.activo;
    }
}