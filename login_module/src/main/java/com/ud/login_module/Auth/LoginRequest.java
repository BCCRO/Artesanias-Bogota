package com.ud.login_module.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una solicitud de inicio de sesión.
 * Contiene las credenciales necesarias para autenticar a un usuario.
 */
@Data // Genera automáticamente los métodos getter, setter, equals, hashCode y toString.
@Builder // Permite construir instancias de la clase utilizando el patrón Builder.
@AllArgsConstructor // Genera un constructor con todos los atributos como parámetros.
@NoArgsConstructor // Genera un constructor sin parámetros.
public class LoginRequest {

  // Dirección de correo electrónico del usuario.
  String email;

  // Contraseña del usuario.
  String password;
}