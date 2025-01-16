package com.ud.login_module.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.login_module.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long>{
  @SuppressWarnings("null")
  Optional<Rol> findById(Long id);
  Optional<Rol> findByRolIgnoreCase(String rol);
}