package com.ud.login_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ud.login_module.models.Transaccion;
import java.util.List;
import java.util.Optional;


public interface TransaccionRepository  extends JpaRepository<Transaccion, Long> {
  @SuppressWarnings("null")
  public Optional<Transaccion> findById(Long id);

  @SuppressWarnings("null")
  public List<Transaccion> findAll();
}
