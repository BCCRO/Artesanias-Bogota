package com.ud.artesanias_bogota.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.ud.artesanias_bogota.models.request.UserRequest;
import com.ud.artesanias_bogota.models.responses.RegisterResponse;
import com.ud.artesanias_bogota.models.responses.UserResponse;
import com.ud.artesanias_bogota.services.UsuarioService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
  private final UsuarioService userService;

  @GetMapping("/list")
  public ResponseEntity<?> getUsuariosList() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("")
  public ResponseEntity<?> getMethodName(@RequestParam String id) {
    try {
      UserResponse usuario = userService.getUser(id);
      if (!"User not found".equals(usuario.getName())) {
        return ResponseEntity.ok(usuario);
      }
      return ResponseEntity.status(404).body("Usuario no encontrado");
      
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(null);
    }
      
  }
  

  @PutMapping("/update")
  public ResponseEntity<?> putMethodName(@RequestParam String id, @RequestBody UserRequest request) {
    try {
            UserResponse updatedUser = userService.updateUser(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Usuario no encontrado")){
              return ResponseEntity.internalServerError().body("Usuario no encontrado");
            }
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
      
  }
  


  @PostMapping(value="/create",produces="application/json")
  public ResponseEntity<?> createUsuario(@RequestBody UserRequest request) {
      RegisterResponse res = userService.create(request);
      if (res.getStatusCode() != 200) {
        return ResponseEntity.internalServerError().body(res);
      }
      return ResponseEntity.ok(res);
  }

  @DeleteMapping("/del")
  public ResponseEntity<?> requestMethodName(@RequestParam String id) {
      if (!userService.deleteUser(id)) {
        return ResponseEntity.internalServerError().body(null);
      }
      return ResponseEntity.ok("Usuario eliminado correctamente");
  }
  
  
}
