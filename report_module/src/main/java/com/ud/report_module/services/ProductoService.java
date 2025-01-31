package com.ud.report_module.services;


import com.ud.report_module.models.Producto;
import com.ud.report_module.models.dtos.ProductoDTO;
import com.ud.report_module.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para manejar la lógica de negocio de los productos.
 * Proporciona métodos para obtener productos por ID y generar reportes de productos más vendidos.
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene un producto por su identificador único.
     * 
     * @param id Identificador del producto.
     * @return Un objeto ProductoDTO con la información del producto o null si no se encuentra.
     */
    public ProductoDTO getProductoById(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);
        return producto.map(p -> new ProductoDTO(
                p.getId(),
                p.getNombre(),
                p.getImagen(),
                p.getPrecioUnitario(),
                p.getDescripcion(),
                p.getCalificacion(),
                p.getIdCategoriaProducto()
        )).orElse(null);
    }

    /**
     * Obtiene los 10 productos más vendidos en un rango de fechas.
     * 
     * @param fechaInicio Fecha de inicio del periodo de ventas.
     * @param fechaFin Fecha de fin del periodo de ventas.
     * @return Lista de los 10 productos más vendidos en formato DTO.
     */
    public List<ProductoDTO> getProductosMasVendidos(String fechaInicio, String fechaFin) {
        List<Producto> productos = productoRepository.findAll(); // Falta filtrar por ventas
        return productos.stream()
                .limit(10)  // Simulación de los 10 productos más vendidos
                .map(p -> new ProductoDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getImagen(),
                        p.getPrecioUnitario(),
                        p.getDescripcion(),
                        p.getCalificacion(),
                        p.getIdCategoriaProducto()
                ))
                .collect(Collectors.toList());
    }
}