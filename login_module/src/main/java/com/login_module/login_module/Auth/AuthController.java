package com.login_module.login_module.Auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login_module.login_module.error.InternalServerErrorResponse;
import com.login_module.login_module.jwt.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  
  private final AuthService authService;
  private final JwtService jwtService;
  
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request){
    try {
      return ResponseEntity.ok(authService.login(request));
    } catch (Exception e) {
      InternalServerErrorResponse response = new InternalServerErrorResponse();
      response.setMessage("Something went wrong");
      response.setError(e);
      return ResponseEntity.internalServerError().body(response);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> userRegister(@RequestBody RegisterRequest request) {
      try {
        return ResponseEntity.ok(authService.register(request));
      } catch (Exception e) {
        return ResponseEntity.internalServerError().body(e);
      }
      
      
  }
  
}
