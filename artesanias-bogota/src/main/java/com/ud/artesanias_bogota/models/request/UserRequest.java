package com.ud.artesanias_bogota.models.request;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
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
  List<String> roles;
}
