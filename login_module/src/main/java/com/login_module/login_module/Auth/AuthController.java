package com.login_module.login_module.Auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.login_module.login_module.error.InternalServerErrorResponse;
import com.login_module.login_module.error.ResponseException;
import com.login_module.login_module.jwt.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    } catch (ResponseException e) {
      switch (e.getStatusCode()) {
        case 404:

          return  ResponseEntity.status(404).body(e);
        case 403:
          return  ResponseEntity.ok(e);
        default:
        return ResponseEntity.internalServerError().body(e);
      }
      
    }
  }
}
