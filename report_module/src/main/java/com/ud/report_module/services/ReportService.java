package com.ud.report_module.services;

import com.ud.report_module.models.dtos.ProductoDTO;
import com.ud.report_module.models.dtos.UsuarioDTO;
import com.ud.report_module.repositories.ProductoRepository;
import com.ud.report_module.repositories.UsuarioRepository;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${report.file.dir}")
    private String reportFileDir;

    /**
     * Genera un reporte en PDF con las ventas y la ubicación de usuarios en un período de tiempo específico.
     *
     * @param puntosVenta Lista de IDs de los puntos de venta (puede ser uno, varios o todos).
     * @param fechaInicio Fecha de inicio del reporte.
     * @param fechaFin Fecha de fin del reporte.
     * @return Ruta del archivo PDF generado.
     */
    public String generateReport(List<Long> puntosVenta, String fechaInicio, String fechaFin) {
        System.out.println("Generando el reporte...");
        try {
            PDDocument document = new PDDocument();
            PDPage firstPage = new PDPage();
            document.addPage(firstPage);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, firstPage)) {
                addHeader(contentStream, fechaInicio, fechaFin);
            }

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
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = String.format("reporte_artesanias_%s.pdf", timestamp);
            String filePath = String.format("%s/%s", reportFileDir, fileName);
            document.save(filePath);
            document.close();
            return fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addHeader(PDPageContentStream contentStream, String fechaInicio, String fechaFin) throws IOException {
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText("ARTESANÍAS BOGOTÁ LTDA - Reporte de Ventas");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, 730);
        contentStream.showText("Rango de fechas: " + fechaInicio + " - " + fechaFin);
        contentStream.endText();
    }

    private List<ProductoDTO> getTopProductos(List<Long> puntosVenta, String fechaInicio, String fechaFin, boolean masVendidos) {
        List<ProductoDTO> productos = productoRepository.findAll().stream()
                .map(p -> ProductoDTO.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .imagen(p.getImagen())
                        .precioUnitario(p.getPrecioUnitario())
                        .descripcion(p.getDescripcion())
                        .calificacion(p.getCalificacion())
                        .idCategoriaProducto(p.getIdCategoriaProducto())
                        .build()
                )
                .collect(Collectors.toList());

        return productos.stream()
                .sorted((p1, p2) -> masVendidos ? Long.compare(p2.getPrecioUnitario(), p1.getPrecioUnitario())
                        : Long.compare(p1.getPrecioUnitario(), p2.getPrecioUnitario()))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Genera gráfico de pastel
    @SuppressWarnings("deprecation")
    private PieChart createPieChart(List<ProductoDTO> productos, String title) {
        PieChart chart = new PieChartBuilder().width(800).height(600).title(title).build();
        chart.getStyler().setLegendPosition(PieStyler.LegendPosition.OutsideE);
        chart.getStyler().setHasAnnotations(true);
        chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndValue);
        chart.getStyler().setPlotBackgroundColor(Color.WHITE);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        for (ProductoDTO producto : productos) {
            String label = producto.getNombre() + " - " + currencyFormat.format(producto.getPrecioUnitario());
            chart.addSeries(label, producto.getPrecioUnitario());
        }
        return chart;
    }

    // Genera gráfico de barras
    @SuppressWarnings("deprecation")
    private CategoryChart createBarChart(List<ProductoDTO> productos, String title) {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(title).xAxisTitle("Productos").yAxisTitle("Ventas").build();
        chart.getStyler().setChartBackgroundColor(Color.decode("#f5f5dc"));
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        List<String> nombres = productos.stream()
                .map(p -> p.getNombre() + " - " + currencyFormat.format(p.getPrecioUnitario()))
                .collect(Collectors.toList());

        List<Long> ventas = productos.stream().map(ProductoDTO::getPrecioUnitario).collect(Collectors.toList());

        chart.addSeries("Ventas", nombres, ventas);
        return chart;
    }

    private void addChartToPDF(PDDocument document, Chart<?, ?> chart) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapEncoder.saveBitmap(chart, baos, BitmapEncoder.BitmapFormat.PNG);
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
                .map(u -> UsuarioDTO.builder()
                      .primerNombre(u.getPrimerNombre())
                      .segundoNombre(u.getSegundoNombre())
                      .primerApellido(u.getPrimerApellido())
                      .segundoApellido(u.getSegundoApellido())
                      .documento(u.getDocumento())
                      .email(u.getEmail())
                      .fechaCreacion(u.getFechaCreacion())
                      .fechaNacimiento(u.getFechaNacimiento())
                      .direccion(u.getDireccion())
                      .telefono(u.getTelefono())
                      .activo(u.isActivo())
                      .latitud(u.getLatitud())
                      .longitud(u.getLongitud())
                      .rol(u.getIdRol())
                      .build())
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