package com.ud.artesanias_bogota.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.artesanias_bogota.models.Transaccion;
import com.ud.artesanias_bogota.repositories.TransaccionRepository;

/**
 * Servicio para la gestión de transacciones.
 * Proporciona métodos para consultar, crear, actualizar y gestionar el estado de las transacciones.
 */
@Service
public class TransaccionService {
  
  @Autowired
  private TransaccionRepository transaccionRepository;

  /**
   * Obtiene una transacción por su ID.
   *
   * @param idTransaccion identificador de la transacción.
   * @return la transacción correspondiente.
   * @throws RuntimeException si no se encuentra la transacción.
   */
  public Transaccion getTransaccion(Long idTransaccion) {
    return transaccionRepository.findById(idTransaccion).orElseThrow();
  }

  /**
   * Obtiene todas las transacciones almacenadas.
   *
   * @return lista de transacciones.
   */
  public List<Transaccion> getTransacciones() {
    return transaccionRepository.findAll();  
  }

  /**
   * Crea una nueva transacción si no existe ya en la base de datos.
   *
   * @param transaccion la transacción a crear.
   * @throws IllegalArgumentException si la transacción ya existe.
   */
  public void createTransaccion(Transaccion transaccion) {
    if (transaccion.getId() != null && transaccionRepository.existsById(transaccion.getId())) {
      throw new IllegalArgumentException("La transacción ya existe");
    }
    try {
      transaccionRepository.saveAndFlush(transaccion);
    } catch (Exception e) {
      System.out.println(e.getMessage()); // TODO: cambiar a un logger.
    }
  }

  /**
   * Actualiza una transacción existente.
   *
   * @param transaccion la transacción con los nuevos datos.
   * @return {@code true} si la actualización fue exitosa, {@code false} si ocurrió un error.
   * @throws IllegalArgumentException si la transacción no existe.
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
   * Actualiza el estado de una transacción y su información en el portal de pagos.
   *
   * @param id                       identificador de la transacción.
   * @param estado                   nuevo estado de la transacción.
   * @param idTransaccionPortalPagos identificador de la transacción en el portal de pagos.
   * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
   * @throws IllegalArgumentException si la transacción no existe.
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
   * Obtiene el estado actual de una transacción por su ID.
   *
   * @param id identificador de la transacción.
   * @return el estado de la transacción como cadena de texto.
   * @throws IllegalArgumentException si la transacción no existe.
   */
  public String getEstadoTransaccion(Long id) {
    if (!transaccionRepository.existsById(id)) {
      throw new IllegalArgumentException("No existe una transacción con el ID otorgado");
    }
    try {
      Transaccion transaccion = transaccionRepository.findById(id).orElseThrow();
      return transaccion.getEstado();
    } catch (Exception e) {
      return ""; // Mejorar manejo de errores y considerar el uso de excepciones personalizadas.
    }
  }
}