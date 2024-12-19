package com.ud.artesanias_bogota.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.artesanias_bogota.models.RolHasUsuario;

@Repository
public interface RolHasUserRepository extends JpaRepository<RolHasUsuario,Long>{
}
