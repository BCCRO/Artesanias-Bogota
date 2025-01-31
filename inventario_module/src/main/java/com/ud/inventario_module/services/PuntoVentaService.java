package com.ud.inventario_module.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.inventario_module.models.PuntoVenta;
import com.ud.inventario_module.models.Usuario;
import com.ud.inventario_module.repositories.PuntoVentaRepository;
import com.ud.inventario_module.repositories.UsuarioRepository;

@Service
public class PuntoVentaService {
  @Autowired
  private PuntoVentaRepository puntoVentaRepo;

  @Autowired
  private UsuarioRepository usuarioRepo;

  /**
   * Método que retorna los puntos de venta cercanos a un usuario
   * 
   * @param userId id del usuario
   * @return lista de id de puntos de venta cercanos
   */
  public List<String> puntosCercanos(String userId) {
    Usuario user = usuarioRepo.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    double[] coordenadasUsuario = new double[] { user.getLatitud(), user.getLongitud() };
    List<PuntoVenta> puntosVenta = puntoVentaRepo.findAll();
    List<double[]> puntosCoordenadas = new ArrayList<>();
    for (PuntoVenta punto : puntosVenta) {
      puntosCoordenadas.add(new double[] { punto.getId(), punto.getLatidud(), punto.getLongitud() });
    }
    return GeocodificacionService.getSortedPointsByProximity(puntosCoordenadas, coordenadasUsuario);
  }
}
