package com.ud.login_module.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una respuesta de error interno del servidor.
 * Contiene información sobre el código de estado y el mensaje de error.
 */
@Data // Genera automáticamente los métodos getter, setter, equals, hashCode y toString.
@Builder // Permite construir instancias de la clase utilizando el patrón Builder.
@AllArgsConstructor // Genera un constructor con todos los atributos como parámetros.
@NoArgsConstructor // Genera un constructor sin parámetros.
public class InternalServerErrorResponse {

  // Código de estado HTTP que describe el error.
  int statusCode;

  // Mensaje descriptivo del error.
  String message;
}