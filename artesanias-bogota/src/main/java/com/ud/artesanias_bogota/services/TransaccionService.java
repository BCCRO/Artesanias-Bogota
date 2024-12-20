package com.ud.artesanias_bogota.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.artesanias_bogota.models.Transaccion;
import com.ud.artesanias_bogota.repositories.TransaccionRepository;

@Service
public class TransaccionService {
  
  @Autowired
  private TransaccionRepository transaccionRepository;

  public Transaccion getTransaccion(Long idTransaccion){
    return transaccionRepository.findById(idTransaccion).orElseThrow();
  }

  public List<Transaccion> getTransacciones(){
    return transaccionRepository.findAll();  
  }

  public void createTransaccion(Transaccion transaccion){
    if(transaccion.getId() != null && transaccionRepository.existsById(transaccion.getId())){
      throw new IllegalArgumentException("La transaccion ya existe");
    }
    System.out.println(transaccion);
    try {
      transaccionRepository.saveAndFlush(transaccion);
    } catch (Exception e) {
      System.out.println(e.getMessage());   //TODO mover a logger
    }
  }

  public boolean updateTransaccion(Transaccion transaccion){
    if(!transaccionRepository.existsById(transaccion.getId())){
      throw new IllegalArgumentException("El Id de la transaccion no existe");
    }

    try {
      transaccionRepository.save(transaccion);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean updateEstadoTransaccion(Long id, String estado, Long idTransaccionPortalPagos){
    if(!transaccionRepository.existsById(id)){
      throw new IllegalArgumentException("No existe una transaccion con el id otorgado");
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

  public String getEstadoTransaccion(Long id){
    if(!transaccionRepository.existsById(id)){
      throw new IllegalArgumentException("No existe una transaccion con el id otorgado");
    }
    try {
      Transaccion transaccion = transaccionRepository.findById(id).orElseThrow();
      return transaccion.getEstado();
    } catch (Exception e) {
      return "";
    }
  }
}
