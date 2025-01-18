package com.ud.pago_module.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.pago_module.models.Transaccion;
import com.ud.pago_module.repositories.TransaccionRepository;

/**
 * Servicio para gestionar las transacciones en el sistema.
 */
@Service
public class TransaccionService {
  
  @Autowired
  private TransaccionRepository transaccionRepository;

  /**
   * Obtiene una transacción por su ID.
   *
   * @param idTransaccion Identificador de la transacción.
   * @return La transacción correspondiente al ID.
   * @throws RuntimeException Si la transacción no es encontrada.
   */
  public Transaccion getTransaccion(Long idTransaccion) {
    return transaccionRepository.findById(idTransaccion)
        .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
  }

  /**
   * Obtiene todas las transacciones del sistema.
   *
   * @return Lista de todas las transacciones.
   */
  public List<Transaccion> getTransacciones() {
    return transaccionRepository.findAll();  
  }

  /**
   * Crea una nueva transacción.
   *
   * @param transaccion La transacción a crear.
   * @throws IllegalArgumentException Si la transacción ya existe.
   */
  public void createTransaccion(Transaccion transaccion) {
    if (transaccion.getId() != null && transaccionRepository.existsById(transaccion.getId())) {
      throw new IllegalArgumentException("La transacción ya existe");
    }
    try {
      transaccionRepository.saveAndFlush(transaccion);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Actualiza una transacción existente.
   *
   * @param transaccion La transacción con los datos actualizados.
   * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
   * @throws IllegalArgumentException Si el ID de la transacción no existe.
   */
  public boolean updateTransaccion(Transaccion transaccion) {
    if (!transaccionRepository.existsById(transaccion.getId())) {
      throw new IllegalArgumentException("El ID de la transacción no existe");
    }
    try {
      transaccionRepository.save(transaccion);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Actualiza el estado de una transacción existente.
   *
   * @param id                       Identificador de la transacción.
   * @param estado                   Nuevo estado de la transacción.
   * @param idTransaccionPortalPagos ID de la transacción en el portal de pagos.
   * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
   * @throws IllegalArgumentException Si la transacción no existe.
   */
  public boolean updateEstadoTransaccion(Long id, String estado, Long idTransaccionPortalPagos) {
    if (!transaccionRepository.existsById(id)) {
      throw new IllegalArgumentException("No existe una transacción con el ID otorgado");
    }
    try {
      Transaccion transaccion = transaccionRepository.findById(id).orElseThrow();
      transaccion.setEstado(estado);
      transaccion.setIdPortalPagos(idTransaccionPortalPagos);
      transaccion.setFechaActualizacion(new Date());
      transaccionRepository.save(transaccion);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Obtiene el estado de una transacción por su ID.
   *
   * @param id Identificador de la transacción.
   * @return El estado de la transacción.
   * @throws IllegalArgumentException Si la transacción no existe.
   */
  public String getEstadoTransaccion(Long id) {
    if (!transaccionRepository.existsById(id)) {
      throw new IllegalArgumentException("No existe una transacción con el ID otorgado");
    }
    try {
      Transaccion transaccion = transaccionRepository.findById(id).orElseThrow();
      return transaccion.getEstado();
    } catch (Exception e) {
      return "";
    }
  }
}