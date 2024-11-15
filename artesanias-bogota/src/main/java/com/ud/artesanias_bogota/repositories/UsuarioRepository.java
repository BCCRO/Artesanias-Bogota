package com.ud.artesanias_bogota.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.artesanias_bogota.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,String>{
  Optional <Usuario> findByEmail(String email);
}
