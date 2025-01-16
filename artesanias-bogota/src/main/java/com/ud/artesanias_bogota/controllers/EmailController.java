package com.ud.artesanias_bogota.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.artesanias_bogota.models.dtos.EmailDTO;
import com.ud.artesanias_bogota.services.EmailService;

import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador REST para el manejo de correos electrónicos.
 * Este controlador expone un endpoint para enviar correos electrónicos utilizando el servicio de correo configurado.
 */
@RestController // Indica que esta clase es un controlador REST.
@RequestMapping("/api") // Define el prefijo para los endpoints expuestos en esta clase.
public class EmailController {

  @Autowired // Inyecta una instancia del servicio de correo.
  private EmailService emailService;

  /**
   * Endpoint para enviar correos electrónicos de autenticación.
   * Recibe un objeto EmailDTO con los datos necesarios para enviar el correo.
   *
   * @param email objeto EmailDTO que contiene los detalles del correo electrónico.
   * @return una ResponseEntity con el estado de la operación y un código de confirmación en caso de éxito.
   * @throws MessagingException si ocurre un error al enviar el correo.
   */
  @PostMapping("/email_auth") // Define un endpoint POST en la ruta "/api/email_auth".
  private ResponseEntity<?> sendEmail(@RequestBody EmailDTO email) throws MessagingException {
    Map<String, Object> response = new HashMap<>(); // Almacena la respuesta que se enviará al cliente.
    System.out.println("Se envió el correo"); // Mensaje de depuración en la consola.

    try {
      // Llama al servicio de correo para enviar el correo y obtiene el código de confirmación.
      int code = emailService.sendMail(email);
      response.put("confirmCode", code); // Agrega el código de confirmación a la respuesta.
      response.put("message", "Ok"); // Agrega un mensaje de éxito a la respuesta.
      return new ResponseEntity<>(response, HttpStatus.OK); // Retorna una respuesta con estado HTTP 200 (OK).
    } catch (Exception e) {
      response.put("message", "Oops, something went wrong!"); // Agrega un mensaje de error a la respuesta.
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Retorna una respuesta con estado HTTP 500 (Error interno).
    }
  }
}

