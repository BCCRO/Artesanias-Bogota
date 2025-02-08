package com.ud.report_module.models.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
  List<Long> puntosVentaIds;
  String fechaInicio;
  String fechaFin;
}
