package com.ud.pago_module.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long>{
  Optional<Rol> findById(Long id);
  Optional<Rol> findByRolIgnoreCase(String rol);
}
