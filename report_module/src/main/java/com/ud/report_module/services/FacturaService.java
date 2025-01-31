package com.ud.report_module.services;

import com.ud.report_module.models.Factura;
import com.ud.report_module.models.dtos.FacturaDTO;
import com.ud.report_module.repositories.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para manejar la lógica de negocio de las facturas.
 * Proporciona métodos para obtener facturas de un usuario y otros reportes relacionados.
 */
@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    /**
     * Obtiene todas las facturas de un usuario específico.
     * 
     * @param documentoUsuario Documento del usuario cuyas facturas se desean consultar.
     * @return Lista de facturas del usuario en formato DTO.
     */
    public List<FacturaDTO> getFacturasByUsuario(String documentoUsuario) {
        List<Factura> facturas = facturaRepository.findAll();
        return facturas.stream()
                .filter(f -> f.getIdUsuarioDocumento().equals(documentoUsuario))
                .map(f -> new FacturaDTO(
                        f.getId(),
                        f.getFechaEmision(),
                        f.getTotal(),
                        f.getTotalImpuesto(),
                        f.getTotaldescuento(),
                        f.getIdUsuarioDocumento(),
                        f.getTransaccionId()
                ))
                .collect(Collectors.toList());
    }
}