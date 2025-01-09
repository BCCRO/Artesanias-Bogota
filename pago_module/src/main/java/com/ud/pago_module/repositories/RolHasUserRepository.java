package com.ud.pago_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.RolHasUsuario;

@Repository
public interface RolHasUserRepository extends JpaRepository<RolHasUsuario,Long>{
}
