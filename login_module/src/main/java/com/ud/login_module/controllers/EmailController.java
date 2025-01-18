package com.ud.login_module.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.login_module.models.dtos.EmailDTO;
import com.ud.login_module.services.EmailService;

import jakarta.mail.MessagingException;

@RestController // Declara esta clase como un controlador REST para manejar solicitudes HTTP.
@RequestMapping("/api") // Define la ruta base "/api" para las solicitudes relacionadas con esta clase.
public class EmailController {

  @Autowired // Inyecta la dependencia del servicio de correos electrónicos.
  EmailService emailService;

  /**
   * Enviar un correo electrónico de autenticación.
   *
   * @param email Un objeto EmailDTO que contiene la información necesaria para enviar el correo.
   * @return Una respuesta HTTP que incluye el código de confirmación en caso de éxito o un mensaje de error en caso de fallo.
   * @throws MessagingException Si ocurre un error relacionado con el envío del correo.
   */
  @PostMapping("/email_auth") // Define un endpoint POST en la ruta "/email_auth".
  private ResponseEntity<?> sendEmail(@RequestBody EmailDTO email) throws MessagingException {
    // Mapa para almacenar la respuesta que se enviará al cliente.
    Map<String, Object> response = new HashMap<>();
    System.out.println("Se envió el correo"); // Mensaje de depuración para indicar que se inició el proceso de envío.

    try {
      // Llama al servicio para enviar el correo y obtiene un código de confirmación.
      int code = emailService.sendMail(email);
      response.put("confirmCode", code); // Agrega el código de confirmación al mapa de respuesta.
      response.put("message", "Ok"); // Mensaje de éxito.
      // Retorna una respuesta HTTP con estado 200 (OK) y el contenido del mapa de respuesta.
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      // Maneja cualquier excepción que ocurra durante el proceso de envío del correo.
      response.put("message", "Oops, something went wrong!"); // Mensaje genérico de error.
      // Retorna una respuesta HTTP con estado 500 (Error interno del servidor).
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}