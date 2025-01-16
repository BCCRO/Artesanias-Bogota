package com.ud.login_module.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<Usuario,String>{
  Optional <Usuario> findByEmail(String email);
}
