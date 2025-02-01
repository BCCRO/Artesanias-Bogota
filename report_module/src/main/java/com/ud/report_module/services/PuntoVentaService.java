package com.ud.report_module.services;

import com.ud.report_module.models.PuntoVenta;
import com.ud.report_module.models.dtos.PuntoVentaDTO;
import com.ud.report_module.repositories.PuntoVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/**
 * Servicio para manejar la lógica de negocio de los puntos de venta.
 * Proporciona métodos para obtener información de puntos de venta por ID o listar todos.
 */
@Service
public class PuntoVentaService {

    @Autowired
    private PuntoVentaRepository puntoVentaRepository;

    /**
     * Obtiene un punto de venta por su identificador único.
     * 
     * @param id Identificador del punto de venta.
     * @return Un objeto PuntoVentaDTO con la información del punto de venta o null si no se encuentra.
     */
    public PuntoVentaDTO getPuntoVentaById(Long id) {
        Optional<PuntoVenta> puntoVenta = puntoVentaRepository.findById(id);
        return puntoVenta.map(p -> PuntoVentaDTO.builder()
                .id( p.getId())
                .nombre(p.getNombre())
                .direccion(p.getDireccion())
                .ciudad(p.getCiudad())
                .departamento(p.getDepartamento())
                .categoriaPuntosVentaId(Integer.parseInt(p.getIdCategoriaPuntoVenta()))
                .latitud(p.getLatitud())
                .longitud(p.getLongitud())
                .build()
        ).orElse(null);
    }

    /**
     * Obtiene la lista de todos los puntos de venta registrados.
     * 
     * @return Lista de todos los puntos de venta en formato DTO.
     */
    public List<PuntoVentaDTO> getAllPuntosVenta() {
        List<PuntoVenta> puntosVenta = puntoVentaRepository.findAll();
        return puntosVenta.stream()
                .map(p -> PuntoVentaDTO.builder()
                        .id( p.getId())
                        .nombre(p.getNombre())
                        .direccion(p.getDireccion())
                        .ciudad(p.getCiudad())
                        .departamento(p.getDepartamento())
                        .categoriaPuntosVentaId(Integer.parseInt(p.getIdCategoriaPuntoVenta()))
                        .latitud(p.getLatitud())
                        .longitud(p.getLongitud())
                        .build()
                )
                .collect(Collectors.toList());
    }
}