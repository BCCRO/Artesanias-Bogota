package com.ud.login_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.login_module.User.RolHasUsuario;

@Repository
public interface RolHasUserRepository extends JpaRepository<RolHasUsuario,Long>{
}
