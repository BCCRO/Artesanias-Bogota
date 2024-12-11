package com.login_module.login_module.Auth;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse{
  String token;
  String documento;
  Date fechaNacimiento;
  Long telefono;
  String primerNombre;
  String segundoNombre;
  String primerApellido;
  String segundoApellido;
  Date fechaCreacion;
  String direccion;
  String email;
  Boolean isActive;
  List<String> roles;
}
