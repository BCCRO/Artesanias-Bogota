package com.ud.artesanias_bogota.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ud.artesanias_bogota.models.dtos.UsuarioDTO;
import com.ud.artesanias_bogota.models.responses.RegisterResponse;
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
      UsuarioDTO usuario = userService.getUser(id);
      if (!"User not found".equals(usuario.getPrimerNombre())) {
        return ResponseEntity.ok(usuario);
      }
      return ResponseEntity.status(404).body("Usuario no encontrado");
      
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(null);
    }
      
  }
  

  @PutMapping("/update")
  public ResponseEntity<?> putMethodName(@RequestParam String id, @RequestBody UsuarioDTO request) {
    try {
            UsuarioDTO updatedUser = userService.updateUser(id, request);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("Usuario no encontrado")){
              return ResponseEntity.internalServerError().body("Usuario no encontrado");
            }
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
      
  }
  


  @PostMapping(value="/create",produces="application/json")
  public ResponseEntity<?> createUsuario(@RequestBody UsuarioDTO request) {
      RegisterResponse res = userService.create(request);
      if (res.getStatusCode() != 200) {
        return ResponseEntity.internalServerError().body(res);
      }
      return ResponseEntity.ok(res);
  }

  @PostMapping(value="/create/cliente",produces="application/json")
  public ResponseEntity<?> createUsuarioCliente(@RequestBody UsuarioDTO request) {
      RegisterResponse res = userService.createCliente(request);
      if (res.getStatusCode() != 200) {
        return ResponseEntity.internalServerError().body(res);
      }
      return ResponseEntity.ok(res);
  }


  @PutMapping("/status/{id}")
  public ResponseEntity<?> requestMethodName(@PathVariable String id) {
      if (!userService.changeUserStatus(id)) {
        return ResponseEntity.internalServerError().body("Hubo un error");
      }
      return ResponseEntity.ok("El estado del usuario se ha cambiado con exito");
  }
  
  
}
