package com.ud.artesanias_bogota.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ud.artesanias_bogota.models.dtos.UsuarioDTO;
import com.ud.artesanias_bogota.models.responses.RegisterResponse;
import com.ud.artesanias_bogota.models.responses.ServerErrorResponse;
import com.ud.artesanias_bogota.services.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Controlador REST para la gestión de usuarios.
 * Proporciona endpoints para crear, consultar, actualizar y cambiar el estado de los usuarios.
 */
@RestController // Indica que esta clase es un controlador REST.
@RequestMapping("/api/usuarios") // Define el prefijo de las rutas para los endpoints relacionados con usuarios.
@RequiredArgsConstructor // Genera automáticamente el constructor para la inyección de dependencias.
public class UsuarioController {

  private final UsuarioService userService; // Servicio de gestión de usuarios.

  /**
   * Obtiene la lista completa de usuarios.
   * 
   * @return una lista de usuarios.
   */
  @GetMapping("/list")
  public ResponseEntity<?> getUsuariosList() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**
   * Obtiene un usuario específico por su ID.
   * 
   * @param id ID del usuario a consultar.
   * @return el usuario correspondiente o un estado HTTP 404 si no se encuentra.
   */
  @GetMapping("")
  public ResponseEntity<?> getMethodName(@RequestParam String id) {
    try {
      UsuarioDTO usuario = userService.getUser(id);
      if (!"User not found".equals(usuario.getPrimerNombre())) {
        return ResponseEntity.ok(usuario);
      }
      return ResponseEntity.status(404).body("Usuario no encontrado");
      
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(null);
    }
  }

  /**
   * Actualiza los datos de un usuario existente.
   * 
   * @param id ID del usuario a actualizar.
   * @param request DTO con los datos actualizados del usuario.
   * @return el usuario actualizado o un estado de error si ocurre un problema.
   */
  @PutMapping("/update")
  public ResponseEntity<?> putMethodName(@RequestParam String id, @RequestBody UsuarioDTO request) {
    try {
      UsuarioDTO updatedUser = userService.updateUser(id, request);
      return ResponseEntity.ok(updatedUser);
    } catch(IllegalArgumentException e) {
      return ResponseEntity.status(409).body(ServerErrorResponse.builder()
        .statusCode(409)
        .message(e.getMessage())
      );
    } catch (RuntimeException e) {
      if (e.getMessage().equals("Usuario no encontrado")) {
        return ResponseEntity.status(404).body(ServerErrorResponse.builder()
        .statusCode(404)
        .message("Usuario no encontrado"));
      }
      return ResponseEntity.internalServerError().body(ServerErrorResponse.builder()
        .statusCode(500)
        .message("Ocurrió un error inesperado"));
    }
  }

  /**
   * Crea un nuevo usuario en el sistema.
   * 
   * @param request DTO con los datos del usuario a crear.
   * @return la respuesta del registro del usuario o un estado HTTP 500 en caso de error.
   */
  @PostMapping(value = "/create", produces = "application/json")
  public ResponseEntity<?> createUsuario(@RequestBody UsuarioDTO request) {
    RegisterResponse res = userService.create(request);
    if (res.getStatusCode() != 200) {
      return ResponseEntity.internalServerError().body(res);
    }
    return ResponseEntity.ok(res);
  }

  /**
   * Crea un nuevo usuario cliente.
   * 
   * @param request DTO con los datos del usuario cliente a crear.
   * @return la respuesta del registro del usuario cliente.
   */
  @PostMapping(value = "/create/cliente", produces = "application/json")
  public ResponseEntity<?> createUsuarioCliente(@RequestBody UsuarioDTO request) {
    RegisterResponse res = userService.createCliente(request);
    return ResponseEntity.status(res.getStatusCode()).body(res);
  }

  /**
   * Cambia el estado de un usuario.
   * 
   * @param id ID del usuario cuyo estado se actualizará.
   * @return un mensaje de éxito o un mensaje de error si el usuario no se encuentra.
   */
  @PutMapping("/status/{id}")
  public ResponseEntity<?> requestMethodName(@PathVariable String id) {
    try {
      return ResponseEntity.ok("El estado del usuario se ha cambiado con éxito");
    } catch (RuntimeException e) {
      return ResponseEntity.status(404).body(ServerErrorResponse.builder()
        .statusCode(404)
        .message("El Usuario con la ID '" + id + "' no se encuentra registrado"));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Ocurrió un error inesperado");
    }
  }
}