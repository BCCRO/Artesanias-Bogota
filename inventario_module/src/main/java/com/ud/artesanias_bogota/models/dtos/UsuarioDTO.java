package com.ud.artesanias_bogota.models.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para manejar la información de un usuario.
 * Contiene los datos necesarios para crear, actualizar y gestionar usuarios en el sistema.
 */
@Data // Genera automáticamente los métodos getter, setter, equals, hashCode y toString.
@Builder // Proporciona un patrón de construcción para la creación de instancias de la clase.
@AllArgsConstructor // Genera un constructor con todos los campos de la clase.
@NoArgsConstructor // Genera un constructor vacío necesario para frameworks y serialización.
public class UsuarioDTO {

    // Documento del usuario (identificación).
    String documento;

    // Fecha de nacimiento del usuario.
    Date fechaNacimiento;

    // Número de teléfono del usuario.
    Long telefono;

    // Primer nombre del usuario.
    String primerNombre;

    // Segundo nombre del usuario.
    String segundoNombre;

    // Primer apellido del usuario.
    String primerApellido;

    // Segundo apellido del usuario.
    String segundoApellido;

    // Fecha de creación del usuario en el sistema.
    Date fechaCreacion;

    // Dirección del usuario.
    String direccion;

    // Contraseña del usuario.
    String contrasenia;

    // Correo electrónico del usuario.
    String email;

    // Estado del usuario (activo o inactivo).
    Boolean isActive;

    // Lista de roles asociados al usuario.
    Integer rol;
}