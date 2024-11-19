package com.ud.artesanias_bogota.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.artesanias_bogota.models.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol,Long>{
  Optional<Rol> findById(Long id);
  Optional<Rol> findByRolIgnoreCase(String rol);
}
