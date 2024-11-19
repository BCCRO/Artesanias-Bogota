package com.ud.artesanias_bogota.models.responses;

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
public class UserResponse {
  String name;
  String documento;
  Long telefono;
  String email;
  Date fechaCreacion;
  Date fechaNacimiento;
  String direccion;
  List<String> roles;
}
