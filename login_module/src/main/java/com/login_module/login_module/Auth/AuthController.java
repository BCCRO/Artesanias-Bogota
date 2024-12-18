package com.login_module.login_module.Auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login_module.login_module.error.InternalServerErrorResponse;
import com.login_module.login_module.error.ResponseException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  
  private final AuthService authService; 
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request ){
    try {
      return ResponseEntity.ok(authService.login(request));
    }catch (ResponseException e){
      switch (e.getStatusCode()) {
        case 404:
          return  ResponseEntity.status(404).body(InternalServerErrorResponse.builder()
            .statusCode(404)
            .message("Usuario no encontrado")
            .build());
        default:
          return ResponseEntity.internalServerError().body(InternalServerErrorResponse.builder()
            .statusCode(500)
            .message("Ocurrio un error desconocido").build());
        }
    }catch (AuthenticationException e){
      return  ResponseEntity.status(403).body(InternalServerErrorResponse.builder()
        .statusCode(403)
        .message("Wrong Password")
        .build());
    }
  }
}
