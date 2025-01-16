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

import com.ud.login_module.models.dtos.UsuarioDTO;
import com.ud.login_module.models.responses.RegisterResponse;
import com.ud.login_module.models.responses.ServerErrorResponse;
import com.ud.login_module.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
  @Autowired
  private UsuarioService userService;

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
      
    }catch (Exception e) {
      return ResponseEntity.internalServerError().body(null);
    }
      
  }
  
  @PutMapping("/update")
  public ResponseEntity<?> putMethodName(@RequestParam String id, @RequestBody UsuarioDTO request) {
    try {
      UsuarioDTO updatedUser = userService.updateUser(id, request);
      return ResponseEntity.ok(updatedUser);
    } catch(IllegalArgumentException e){
      return ResponseEntity.status(409).body(ServerErrorResponse.builder()
        .statusCode(409)
        .message(e.getMessage())
      );
    }catch (RuntimeException e) {
      if (e.getMessage().equals("Usuario no encontrado")){
          return ResponseEntity.status(404).body(ServerErrorResponse.builder()
        .statusCode(404)
        .message("Usuario no encontrado"));
        }
        return ResponseEntity.internalServerError().body(ServerErrorResponse.builder()
        .statusCode(500)
        .message("Ocurrio un error inesperado"));
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
      return ResponseEntity.status(res.getStatusCode()).body(res);
  }


  @PutMapping("/status/{id}")
  public ResponseEntity<?> requestMethodName(@PathVariable String id) {
    try {
      return ResponseEntity.ok("El estado del usuario se ha cambiado con exito");
    } catch(RuntimeException e){
      return ResponseEntity.status(404).body(ServerErrorResponse.builder()
        .statusCode(404)
        .message("El Usuario con la id '"+id+"' no se encuentra registrado"));
    }catch (Exception e) {
      return ResponseEntity.internalServerError().body("Ocurrio un error inesperado");
    }
  }
  
  
}
