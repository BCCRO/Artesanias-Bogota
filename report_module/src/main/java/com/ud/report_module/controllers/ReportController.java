package com.ud.report_module.controllers;

import com.ud.report_module.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/generar")
    public ResponseEntity<String> generarReporte(
            @RequestParam List<Long> puntosVentaIds,
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        
        String pdfPath = reportService.generateReport(puntosVentaIds, fechaInicio, fechaFin);
        return ResponseEntity.ok(pdfPath);
    }
}