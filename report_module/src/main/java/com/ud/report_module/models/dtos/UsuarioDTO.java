package com.ud.report_module.models.dtos;


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
    private String documento;

    // Fecha de nacimiento del usuario.
    private Date fechaNacimiento;

    // Número de teléfono del usuario.
    private Long telefono;

    // Primer nombre del usuario.
    private String primerNombre;

    // Segundo nombre del usuario.
    private String segundoNombre;

    // Primer apellido del usuario.
    private String primerApellido;

    // Segundo apellido del usuario.
    private String segundoApellido;

    // Fecha de creación del usuario en el sistema.
    private Date fechaCreacion;

    // Dirección del usuario.
    private String direccion;

    // Correo electrónico del usuario.
    private String email;

    // Estado del usuario (activo o inactivo).
    private boolean activo;

    // Coordenadas del usuario (Ubicación).
    private Double longitud;
    private Double latitud;

    // Identificador del rol asociado al usuario.
    private int rol;
}