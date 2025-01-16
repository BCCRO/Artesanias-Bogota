package com.ud.login_module.models.dtos;

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
public class UsuarioDTO {
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
  Boolean isActive;
  List<String> roles;
}
