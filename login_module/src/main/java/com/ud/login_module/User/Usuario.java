package com.ud.login_module.User;

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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="usuarios", schema = "artesanias_bogota")
public class Usuario implements UserDetails{

    @Id
    @Column(name = "documento", unique = true)
    private String documento;

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @Column(name="telefono", nullable = false, unique = true)
    private Long telefono;

    @Column(name="primer_nombre", nullable = false)
    private String primerNombre;
    @Column(name="segundo_nombre")
    private String segundoNombre;
    @Column(name="primer_apellido", nullable = false)
    private String primerApellido;
    @Column(name="segundo_apellido", nullable = false)
    private String segundoApellido;

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_creacion", nullable = false)
    private Date fechaCreacion;

    @Column(name="direccion", nullable = false)
    private String direccion;

    @Column(name="contrasenia", nullable = false)
    private String contrasenia;

    @Column(name="email", nullable = false, unique = true)
    private String email;
    
    @Column(name="activo", nullable = false)
    private boolean activo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentoUsuario")
    private Set<RolHasUsuario> rolesUsuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return rolesUsuario.stream()
            .map(rol -> new SimpleGrantedAuthority(rol.getRol().getRol()))
            .toList();
    }

    @Override
    public String getPassword() {
      return getContrasenia();
    }

    @Override
    public String getUsername() {
      return getEmail();
    }

    public void chageStatus() {
        this.activo = !this.activo;
    }
}