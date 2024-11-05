package com.login_module.login_module.Auth;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  String documento;
  Date fechaNacimiento;
  Long telefono;
  String primerNombre;
  String segundoNombre;
  String primerApellido;
  String segundoApellido;
  Date fechaCreacion;
  String direccion;
  String contrasenia;
  String email;
}
