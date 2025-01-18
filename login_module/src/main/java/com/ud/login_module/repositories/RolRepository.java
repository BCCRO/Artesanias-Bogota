package com.ud.login_module.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.login_module.User.Rol;

/**
 * Repositorio para la entidad Rol.
 * Proporciona métodos para realizar operaciones CRUD y consultas específicas en la base de datos.
 */
@Repository // Marca esta interfaz como un componente de repositorio gestionado por Spring.
public interface RolRepository extends JpaRepository<Rol, Long> {

  /**
   * Busca un rol por su ID.
   *
   * @param id El ID del rol a buscar.
   * @return Un Optional que contiene el rol si se encuentra.
   */
  @SuppressWarnings("null") // Suprime advertencias relacionadas con valores nulos.
  Optional<Rol> findById(Long id);

  /**
   * Busca un rol por su nombre, ignorando mayúsculas y minúsculas.
   *
   * @param rol El nombre del rol a buscar.
   * @return Un Optional que contiene el rol si se encuentra.
   */
  Optional<Rol> findByRolIgnoreCase(String rol);
}