package com.ud.login_module.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad Usuario.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas relacionadas con los usuarios.
 */
public interface UserRepository extends JpaRepository<Usuario, String> {

  /**
   * Busca un usuario por su correo electrónico.
   *
   * @param email El correo electrónico del usuario.
   * @return Un Optional que contiene el usuario si se encuentra, de lo contrario está vacío.
   */
  Optional<Usuario> findByEmail(String email);
}