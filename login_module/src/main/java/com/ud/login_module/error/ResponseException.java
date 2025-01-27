package com.ud.login_module.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter // Genera automáticamente los métodos getter para los atributos de la clase.
@AllArgsConstructor // Genera un constructor con todos los atributos como parámetros.
public class ResponseException extends RuntimeException {

  // Código de estado HTTP asociado con la excepción.
  int statusCode;

  /**
   * Constructor que inicializa la excepción con un mensaje y un código de estado.
   *
   * @param message Mensaje descriptivo del error.
   * @param statusCode Código de estado HTTP asociado con la excepción.
   */
  public ResponseException(String message, int statusCode) {
    super(message); // Llama al constructor de la clase padre RuntimeException.
    this.statusCode = statusCode; // Inicializa el código de estado.
  }
}