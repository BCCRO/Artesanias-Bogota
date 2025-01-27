package com.ud.login_module.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la respuesta al registro de un usuario.
 * Contiene información sobre el estado del registro y los detalles del usuario registrado.
 */
@Data // Genera automáticamente los métodos getter, setter, equals, hashCode y toString.
@Builder // Permite construir instancias de la clase utilizando el patrón Builder.
@AllArgsConstructor // Genera un constructor con todos los atributos como parámetros.
@NoArgsConstructor // Genera un constructor sin parámetros.
public class RegisterResponse {

  // Código de estado HTTP que describe el resultado del registro.
  int statusCode;

  // Mensaje descriptivo del resultado del registro.
  String message;

  // Identificador único del usuario registrado.
  String userId;

  // Nombre del usuario registrado.
  String userName;
}