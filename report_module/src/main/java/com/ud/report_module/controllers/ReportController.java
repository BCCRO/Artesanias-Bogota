package com.ud.report_module.controllers;

import com.ud.report_module.models.dtos.ReportDTO;
import com.ud.report_module.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;


/**
 * Controlador para gestionar la generación de reportes descriptivos de ventas.
 * Permite generar informes basados en la selección de puntos de venta y un rango de fechas.
 */
@RestController
@RequestMapping("/api/reportes")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Value("${report.file.dir}")
    private String reportFileDir;

    @GetMapping("/descargar-reporte")
    public ResponseEntity<FileSystemResource> descargarReporte(@RequestParam String nombreArchivo) {

        String pathFile = String.format("%s/%s", reportFileDir, nombreArchivo);

        System.out.println(pathFile);

        File archivo = new File(pathFile);

        if (!archivo.exists()) {
            System.out.println("El archivo no existe");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        FileSystemResource recurso = new FileSystemResource(archivo);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + archivo.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(recurso);
    }

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
}