package com.ud.login_module.repositories;

import java.util.Optional;

import com.ud.login_module.User.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String>{
  Optional <Usuario> findByEmail(String email);
  boolean existsByDocumento(String documento);
  boolean existsByTelefono(Long telefono);
  boolean existsByEmail(String email);
}
