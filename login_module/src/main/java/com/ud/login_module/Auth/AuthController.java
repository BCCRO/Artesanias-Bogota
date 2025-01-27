package com.ud.login_module.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ud.login_module.error.InternalServerErrorResponse;
import com.ud.login_module.error.ResponseException;

import lombok.RequiredArgsConstructor;

@RestController // Declara esta clase como un controlador REST para manejar solicitudes HTTP.
@RequestMapping("/auth") // Define la ruta base "/auth" para las solicitudes relacionadas con autenticación.
@RequiredArgsConstructor // Genera un constructor para las dependencias finales definidas en la clase.
public class AuthController {

  // Servicio de autenticación inyectado a través de RequiredArgsConstructor.
  private final AuthService authService; 

  /**
   * Endpoint para iniciar sesión en el sistema.
   * 
   * @param request Un objeto LoginRequest que contiene las credenciales del usuario.
   * @return Una respuesta HTTP con el resultado de la autenticación.
   */
  @PostMapping("/login") // Define un endpoint POST en la ruta "/login".
  public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
      // Llama al servicio de autenticación para validar las credenciales del usuario.
      return ResponseEntity.ok(authService.login(request));
    } catch (ResponseException e) {
      // Maneja excepciones personalizadas relacionadas con errores en el servicio de autenticación.
      switch (e.getStatusCode()) {
        case 404: // Retorna un error 404 si el usuario no es encontrado.
          return ResponseEntity.status(404).body(InternalServerErrorResponse.builder()
            .statusCode(404)
            .message("Usuario no encontrado")
            .build());
        default: // Retorna un error 500 para cualquier otro error desconocido.
          return ResponseEntity.internalServerError().body(InternalServerErrorResponse.builder()
            .statusCode(500)
            .message("Ocurrió un error desconocido")
            .build());
      }
    } catch (AuthenticationException e) {
      // Maneja excepciones de autenticación, como contraseñas incorrectas.
      return ResponseEntity.status(403).body(InternalServerErrorResponse.builder()
        .statusCode(403)
        .message("Contraseña incorrecta")
        .build());
    }
  }
}