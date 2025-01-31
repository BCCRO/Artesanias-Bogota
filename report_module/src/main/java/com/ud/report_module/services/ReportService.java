package com.ud.report_module.services;

import com.ud.report_module.models.dtos.FacturaDTO;
import com.ud.report_module.models.dtos.ProductoDTO;
import com.ud.report_module.models.dtos.UsuarioDTO;
import com.ud.report_module.repositories.FacturaRepository;
import com.ud.report_module.repositories.ProductoRepository;
import com.ud.report_module.repositories.UsuarioRepository;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.knowm.xchart.*;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Genera un reporte en PDF con las ventas y la ubicación de usuarios en un período de tiempo específico.
     *
     * @param puntosVenta Lista de IDs de los puntos de venta (puede ser uno, varios o todos).
     * @param fechaInicio Fecha de inicio del reporte.
     * @param fechaFin Fecha de fin del reporte.
     * @return Ruta del archivo PDF generado.
     */
    public String generateReport(List<Long> puntosVenta, String fechaInicio, String fechaFin) {
        try {
            PDDocument document = new PDDocument();

            // Obtener datos de ventas
            List<ProductoDTO> productosMasVendidos = getTopProductos(puntosVenta, fechaInicio, fechaFin, true);
            List<ProductoDTO> productosMenosVendidos = getTopProductos(puntosVenta, fechaInicio, fechaFin, false);

            // Crear gráficos
            PieChart pieChartMasVendidos = createPieChart(productosMasVendidos, "Top 10 Productos Más Vendidos");
            PieChart pieChartMenosVendidos = createPieChart(productosMenosVendidos, "Top 10 Productos Menos Vendidos");
            CategoryChart barChartMasVendidos = createBarChart(productosMasVendidos, "Total Vendido - Top 10 Productos Más Vendidos");
            CategoryChart barChartMenosVendidos = createBarChart(productosMenosVendidos, "Total Vendido - Top 10 Productos Menos Vendidos");

            // Agregar gráficos al PDF
            addChartToPDF(document, pieChartMasVendidos);
            addChartToPDF(document, pieChartMenosVendidos);
            addChartToPDF(document, barChartMasVendidos);
            addChartToPDF(document, barChartMenosVendidos);

            // Agregar información sobre ubicación de usuarios
            addUserLocationsToPDF(document, fechaInicio, fechaFin);

            // Agregar marca de agua
            addWatermark(document, "Artesanías Bogotá LTDA");

            // Guardar el PDF
            String filePath = "./reporte_artesanias.pdf";
            document.save(filePath);
            document.close();
            return filePath;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para obtener los 10 productos más o menos vendidos
    private List<ProductoDTO> getTopProductos(List<Long> puntosVenta, String fechaInicio, String fechaFin, boolean masVendidos) {
        List<ProductoDTO> productos = productoRepository.findAll().stream()
                .map(p -> new ProductoDTO(p.getNombre(), p.getImagen(), p.getPrecioUnitario(),
                        p.getDescripcion(), p.getCalificacion(), p.getIdCategoriaProducto()))
                .collect(Collectors.toList());

        return productos.stream()
                .sorted((p1, p2) -> masVendidos ? Long.compare(p2.getPrecioUnitario(), p1.getPrecioUnitario()) 
                                                : Long.compare(p1.getPrecioUnitario(), p2.getPrecioUnitario()))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Genera gráfico de pastel
    private PieChart createPieChart(List<ProductoDTO> productos, String title) {
        PieChart chart = new PieChartBuilder().width(800).height(600).title(title).build();
        chart.getStyler().setLegendPosition(PieStyler.LegendPosition.OutsideE);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setChartBackgroundColor(Color.decode("#f5f5dc"));

        for (ProductoDTO producto : productos) {
            chart.addSeries(producto.getNombre(), producto.getPrecioUnitario());
        }
        return chart;
    }

    // Genera gráfico de barras
    private CategoryChart createBarChart(List<ProductoDTO> productos, String title) {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(title).xAxisTitle("Productos").yAxisTitle("Ventas").build();
        chart.getStyler().setChartBackgroundColor(Color.decode("#f5f5dc"));
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        List<String> nombres = productos.stream().map(ProductoDTO::getNombre).collect(Collectors.toList());
        List<Long> ventas = productos.stream().map(ProductoDTO::getPrecioUnitario).collect(Collectors.toList());

        chart.addSeries("Ventas", nombres, ventas);
        return chart;
    }

    // Agregar gráfico al PDF
    private void addChartToPDF(PDDocument document, Object chart) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapEncoder.saveBitmap((Chart<?>) chart, baos, BitmapEncoder.BitmapFormat.PNG);
        PDImageXObject image = PDImageXObject.createFromByteArray(document, baos.toByteArray(), "chart");
        PDPage page = new PDPage();
        document.addPage(page);
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.drawImage(image, 100, 200, 400, 300);
        }
    }

    // Agregar la ubicación de los usuarios al PDF
    private void addUserLocationsToPDF(PDDocument document, String fechaInicio, String fechaFin) throws IOException {
        List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream()
                .filter(u -> u.getFechaCreacion().toString().compareTo(fechaInicio) >= 0 &&
                        u.getFechaCreacion().toString().compareTo(fechaFin) <= 0)
                .map(u -> new UsuarioDTO(u.getDocumento(), u.getFechaNacimiento(), u.getTelefono(),
                        u.getPrimerNombre(), u.getSegundoNombre(), u.getPrimerApellido(),
                        u.getSegundoApellido(), u.getFechaCreacion(), u.getDireccion(),
                        u.getEmail(), u.isActive()))
                .collect(Collectors.toList());

        PDPage page = new PDPage();
        document.addPage(page);
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.showText("Ubicación de Usuarios:");
            contentStream.endText();

            int y = 680;
            for (UsuarioDTO usuario : usuarios) {
                contentStream.beginText();
                contentStream.newLineAtOffset(100, y);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(usuario.getPrimerNombre() + " " + usuario.getPrimerApellido());
                contentStream.endText();
                y -= 20;
            }
        }
    }

    // Agregar marca de agua
    private void addWatermark(PDDocument document, String text) throws IOException {
        for (PDPage page : document.getPages()) {
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 30);
                contentStream.setNonStrokingColor(new Color(200, 200, 200, 100));
                contentStream.newLineAtOffset(200, 400);
                contentStream.showText(text);
                contentStream.endText();
            }
        }
    }
}