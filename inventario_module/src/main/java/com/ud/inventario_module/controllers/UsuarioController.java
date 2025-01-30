package com.ud.inventario_module.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ud.inventario_module.models.dtos.UsuarioDTO;
import com.ud.inventario_module.models.responses.RegisterResponse;
import com.ud.inventario_module.models.responses.ServerErrorResponse;
import com.ud.inventario_module.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController // Declara esta clase como un controlador REST para manejar solicitudes relacionadas con usuarios.
@RequestMapping("/api/usuarios") // Define la ruta base "/api/usuarios" para las solicitudes dirigidas a este controlador.
@RequiredArgsConstructor // Genera un constructor para los campos finales (final) utilizando Lombok.
public class UsuarioController {
  
  // Servicio para manejar la lógica relacionada con usuarios.
  private final UsuarioService userService;

  /**
   * Obtiene una lista de todos los usuarios registrados.
   * @return Una lista de usuarios con estado 200 (OK).
   */
  @GetMapping("/list")
  public ResponseEntity<?> getUsuariosList() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**
   * Obtiene los detalles de un usuario por su ID.
   * @param id El ID del usuario a buscar.
   * @return Los detalles del usuario o un mensaje de error si no se encuentra.
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
   * Actualiza los datos de un usuario por su ID.
   * @param id El ID del usuario a actualizar.
   * @param request Los datos actualizados del usuario.
   * @return El usuario actualizado o un mensaje de error.
   */
  @PutMapping("/update")
  public ResponseEntity<?> putMethodName(@RequestParam String id, @RequestBody UsuarioDTO request) {
    try {
      UsuarioDTO updatedUser = userService.updateUser(id, request);
      return ResponseEntity.status(201).body(updatedUser);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(409).body(ServerErrorResponse.builder()
        .statusCode(409)
        .message(e.getMessage())
      );
    } catch (RuntimeException e) {
      if ("Usuario no encontrado".equals(e.getMessage())) {
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
   * Crea un nuevo usuario.
   * @param request Los datos del usuario a crear.
   * @return La respuesta del proceso de registro con un estado adecuado.
   */
  @PostMapping(value = "/create", produces = "application/json")
  public ResponseEntity<?> createUsuario(@RequestBody UsuarioDTO request) {
    RegisterResponse res = userService.create(request);
    if (res.getStatusCode() != 200) {
      return ResponseEntity.status(res.getStatusCode()).body(res);
    }
    return ResponseEntity.status(201).body(res);
  }

  /**
   * Crea un nuevo usuario con rol de cliente.
   * @param request Los datos del cliente a crear.
   * @return La respuesta del proceso de registro con un estado adecuado.
   */
  @PostMapping(value = "/create/cliente", produces = "application/json")
  public ResponseEntity<?> createUsuarioCliente(@RequestBody UsuarioDTO request) {
    RegisterResponse res = userService.createCliente(request);
    return ResponseEntity.status(res.getStatusCode()).body(res);
  }

  /**
   * Cambia el estado de un usuario por su ID.
   * @param id El ID del usuario cuyo estado se desea cambiar.
   * @return Un mensaje de éxito o error dependiendo del resultado.
   */
  @PutMapping("/status/{id}")
  public ResponseEntity<?> requestMethodName(@PathVariable String id) {
    try {
      // Llama al servicio para cambiar el estado del usuario.
      return ResponseEntity.ok("El estado del usuario se ha cambiado con éxito");
    } catch (RuntimeException e) {
      // Retorna un mensaje de error si el usuario no se encuentra.
      return ResponseEntity.status(404).body(ServerErrorResponse.builder()
        .statusCode(404)
        .message("El usuario con la ID '" + id + "' no se encuentra registrado"));
    } catch (Exception e) {
      // Retorna un mensaje genérico de error en caso de fallo inesperado.
      return ResponseEntity.internalServerError().body("Ocurrió un error inesperado");
    }
  }


}