package com.ud.login_module.repositories;

import java.util.Optional;

import com.ud.login_module.User.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Usuario.
 * Proporciona métodos para realizar operaciones CRUD y consultas específicas relacionadas con usuarios.
 */
@Repository // Marca esta interfaz como un componente de repositorio gestionado por Spring.
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

  /**
   * Busca un usuario por su correo electrónico.
   *
   * @param email El correo electrónico del usuario.
   * @return Un Optional que contiene el usuario si se encuentra.
   */
  Optional<Usuario> findByEmail(String email);

  /**
   * Verifica si existe un usuario con el documento especificado.
   *
   * @param documento El número de documento del usuario.
   * @return true si existe un usuario con el documento, de lo contrario false.
   */
  boolean existsByDocumento(String documento);

  /**
   * Verifica si existe un usuario con el número de teléfono especificado.
   *
   * @param telefono El número de teléfono del usuario.
   * @return true si existe un usuario con el teléfono, de lo contrario false.
   */
  boolean existsByTelefono(Long telefono);

  /**
   * Verifica si existe un usuario con el correo electrónico especificado.
   *
   * @param email El correo electrónico del usuario.
   * @return true si existe un usuario con el correo, de lo contrario false.
   */
  boolean existsByEmail(String email);
}