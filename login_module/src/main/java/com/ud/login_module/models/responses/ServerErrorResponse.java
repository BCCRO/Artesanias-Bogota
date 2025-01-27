package com.ud.login_module.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una respuesta de error del servidor.
 * Se utiliza para encapsular información sobre el estado del error y su descripción.
 */
@Data // Genera automáticamente los métodos getter, setter, equals, hashCode y toString.
@Builder // Permite construir instancias de la clase utilizando el patrón Builder.
@AllArgsConstructor // Genera un constructor con todos los atributos como parámetros.
@NoArgsConstructor // Genera un constructor sin parámetros.
public class ServerErrorResponse {

  // Código de estado HTTP asociado con el error.
  int statusCode;

  // Mensaje descriptivo del error.
  String message;
}