package com.ud.login_module.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ud.login_module.models.dtos.PasswordDTO;
import com.ud.login_module.models.dtos.UsuarioDTO;
import com.ud.login_module.models.responses.RegisterResponse;
import com.ud.login_module.models.responses.ServerErrorResponse;
import com.ud.login_module.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController // Declara esta clase como un controlador REST para manejar solicitudes HTTP.
@RequestMapping("/api/usuarios") // Define la ruta base "/api/usuarios" para las solicitudes relacionadas con usuarios.
@RequiredArgsConstructor // Genera un constructor para los campos finales declarados en la clase.
public class UsuarioController {

  @Autowired // Inyecta la dependencia del servicio de usuarios.
  private UsuarioService userService;

  /**
   * Obtiene la lista de todos los usuarios.
   * 
   * @return Una lista de usuarios o un mensaje de error.
   */
  @GetMapping("/list")
  public ResponseEntity<?> getUsuariosList() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**
   * Obtiene la información de un usuario por su ID.
   * 
   * @param id ID del usuario.
   * @return Información del usuario o un mensaje de error si no se encuentra.
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
   * Actualiza la información de un usuario.
   * 
   * @param id      ID del usuario a actualizar.
   * @param request Datos actualizados del usuario.
   * @return Información del usuario actualizado o un mensaje de error.
   */
  @PutMapping(value = "/update", produces = "application/json")
  public ResponseEntity<?> updateUser(@RequestParam String id, @RequestBody UsuarioDTO request) {
    try {
      UsuarioDTO updatedUser = userService.updateUser(id, request);
      return ResponseEntity.ok(updatedUser);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(409).body(ServerErrorResponse.builder()
        .statusCode(409)
        .message(e.getMessage())
        .build());
    } catch (RuntimeException e) {
      if (e.getMessage().equals("Usuario no encontrado")) {
        return ResponseEntity.status(404).body(ServerErrorResponse.builder()
          .statusCode(404)
          .message("Usuario no encontrado")
          .build());
      }
      return ResponseEntity.internalServerError().body(ServerErrorResponse.builder()
        .statusCode(500)
        .message("Ocurrió un error inesperado")
        .build());
    }
  }

  /**
   * Crea un nuevo usuario.
   * 
   * @param request Datos del nuevo usuario.
   * @return Respuesta de registro o un mensaje de error.
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
   * Crea un nuevo cliente como usuario.
   * 
   * @param request Datos del cliente.
   * @return Respuesta de registro o un mensaje de error.
   */
  @PostMapping(value = "/create/cliente", produces = "application/json")
  public ResponseEntity<?> createUsuarioCliente(@RequestBody UsuarioDTO request) {
    RegisterResponse res = userService.createCliente(request);
    return ResponseEntity.status(res.getStatusCode()).body(res);
  }

  /**
   * Cambia el estado de un usuario.
   * 
   * @param id ID del usuario.
   * @return Mensaje de éxito o error dependiendo del resultado.
   */
  @PutMapping("/status/{id}")
  public ResponseEntity<?> requestMethodName(@PathVariable String id) {
    try {
      return ResponseEntity.ok("El estado del usuario se ha cambiado con éxito");
    } catch (RuntimeException e) {
      return ResponseEntity.status(404).body(ServerErrorResponse.builder()
        .statusCode(404)
        .message("El Usuario con la ID '" + id + "' no se encuentra registrado")
        .build());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Ocurrió un error inesperado");
    }
  }

  /**
   * Actualiza la contraseña de un usuario
   * @param id UD del usuario
   * @param request Cupooer que contiene la nueva contraseña
   * @return Mensaje de exito o error, dependidendo del resultado
   */

  @PutMapping(value="/change_password/{id}")
  public ResponseEntity<?> updatePassword(@PathVariable String id, @RequestBody PasswordDTO request) {
      try {
        boolean res = userService.updatePassword(id, request.getPassword(), request.getNewPassword());
        if (!res) throw new Exception("No se puedo actualizar la contraseña");
        return ResponseEntity.ok("Se modifico la contraseña con exito");
      }catch(IllegalArgumentException e){
        return ResponseEntity
        .status(409)
        .body(ServerErrorResponse.builder()
        .statusCode(409)
        .message(e.getMessage())
        .build());
      }catch (Exception e) {
        return ResponseEntity
        .status(500)
        .body(ServerErrorResponse.builder()
        .statusCode(500)
        .message(e.getMessage())
        .build());
      }
  }

  @GetMapping("/healthcheck")
  public ResponseEntity<?> getHealthCheck(){
    return ResponseEntity.ok("The Login Module is UP");
  }
}