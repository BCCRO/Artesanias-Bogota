package com.ud.artesanias_bogota.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.artesanias_bogota.models.dtos.EmailDTO;
import com.ud.artesanias_bogota.services.EmailService;

import jakarta.mail.MessagingException;

@RestController // Declara esta clase como un controlador REST para manejar solicitudes HTTP.
@RequestMapping("/api") // Define la ruta base para las solicitudes dirigidas a este controlador.
public class EmailController {

  @Autowired // Inyecta la dependencia del servicio de correos electrónicos.
  EmailService emailService;

  @PostMapping("/email_auth") // Define un endpoint para manejar solicitudes POST en la ruta "/email_auth".
  private ResponseEntity<?> sendEmail(@RequestBody EmailDTO email) throws MessagingException {
    // Crea un mapa para almacenar la respuesta que se enviará al cliente.
    Map<String, Object> response = new HashMap<>();
    System.out.println("Se envió el correo"); // Imprime un mensaje en la consola para depuración.

    try {
      // Llama al servicio para enviar un correo electrónico y obtiene un código de confirmación.
      int code = emailService.sendMail(email);
      response.put("confirmCode", code); // Agrega el código de confirmación al mapa de respuesta.
      response.put("message", "Ok"); // Indica que el proceso fue exitoso.
      return new ResponseEntity<>(response, HttpStatus.OK); // Devuelve una respuesta HTTP con estado 200 (OK).
    } catch (Exception e) {
      // Maneja cualquier excepción que ocurra durante el proceso.
      response.put("message", "Oops, something went wrong!"); // Agrega un mensaje de error genérico al mapa de respuesta.
      return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Devuelve una respuesta HTTP con estado 500 (Error Interno del Servidor).
    }
  }
}
