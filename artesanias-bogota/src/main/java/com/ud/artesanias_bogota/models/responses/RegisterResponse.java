package com.ud.artesanias_bogota.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
  int statusCode;
  String message;
  String userId;
  String userName;
  Exception e;
}
