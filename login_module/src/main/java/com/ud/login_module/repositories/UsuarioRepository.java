package com.ud.login_module.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.login_module.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String>{
  Optional <Usuario> findByEmail(String email);
  boolean existsByDocumento(String documento);
  boolean existsByTelefono(Long telefono);
  boolean existsByEmail(String email);
}
