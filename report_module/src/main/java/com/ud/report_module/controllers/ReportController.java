package com.ud.report_module.controllers;

import com.ud.report_module.models.dtos.ReportDTO;
import com.ud.report_module.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador para gestionar la generación de reportes descriptivos de ventas.
 * Permite generar informes basados en la selección de puntos de venta y un rango de fechas.
 */
@RestController
@RequestMapping("/api/reportes")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Genera un reporte en PDF con estadísticas de ventas de productos.
     * @param puntosVentaIds Lista de identificadores de los puntos de venta (0 para todos).
     * @param fechaInicio Fecha de inicio del rango de consulta (formato YYYY-MM-DD).
     * @param fechaFin Fecha de fin del rango de consulta (formato YYYY-MM-DD).
     * @return Ruta del archivo PDF generado.
     */

    @PostMapping("/generar")
    public ResponseEntity<String> generarReporte(@RequestBody ReportDTO request) {
      try {
        System.out.println("entramos");
        String pdfPath = reportService.generateReport(request.getPuntosVentaIds(), request.getFechaInicio(), request.getFechaFin());
        return ResponseEntity.ok(pdfPath);
      } catch (Exception e) {
        System.out.println(e);
        return ResponseEntity.internalServerError().body("Something went wrong");
      }
    }

    @GetMapping("/healthcheck")
    public ResponseEntity<?> getHealthCheck(){
      return ResponseEntity.ok("The Report Module is UP");
    }
}