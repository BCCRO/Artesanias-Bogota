package com.ud.artesanias_bogota.models;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="usuarios")
public class Usuario {

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

    @Column(name="contrasena", nullable = false)
    private String contrasena;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documentoUsuario")
    private Set<RolHasUsuario> rolesUsuario;

    public Usuario() {
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Set<RolHasUsuario> getRolesUsuario() {
        return rolesUsuario;
    }

    public void setRolesUsuario(Set<RolHasUsuario> rolesUsuario) {
        this.rolesUsuario = rolesUsuario;
    }
}
