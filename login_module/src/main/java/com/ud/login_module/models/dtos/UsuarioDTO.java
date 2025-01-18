package com.ud.login_module.models.dtos;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para representar los datos de un usuario.
 * Se utiliza para transferir información del usuario entre capas de la aplicación.
 */
@Data // Genera automáticamente los métodos getter, setter, equals, hashCode y toString.
@Builder // Permite construir instancias de la clase utilizando el patrón Builder.
@AllArgsConstructor // Genera un constructor con todos los atributos como parámetros.
@NoArgsConstructor // Genera un constructor sin parámetros.
public class UsuarioDTO {

  // Documento de identidad del usuario.
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

  // Estado de actividad del usuario (activo/inactivo).
  Boolean isActive;

  // Lista de roles asignados al usuario.
  Integer rol;
}