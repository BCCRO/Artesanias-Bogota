package com.ud.artesanias_bogota.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ud.artesanias_bogota.models.Transaccion;
import java.util.List;
import java.util.Optional;


public interface TransaccionRepository  extends JpaRepository<Transaccion, Long> {
  public Optional<Transaccion> findById(Long id);

  public List<Transaccion> findAll();
}
