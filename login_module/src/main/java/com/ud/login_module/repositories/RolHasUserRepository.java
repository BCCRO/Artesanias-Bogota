package com.ud.login_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.login_module.User.RolHasUsuario;

/**
 * Repositorio para la entidad RolHasUsuario.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos.
 */
@Repository // Marca esta interfaz como un componente de repositorio gestionado por Spring.
public interface RolHasUserRepository extends JpaRepository<RolHasUsuario, Long> {
  // Esta interfaz hereda métodos de JpaRepository para interactuar con la base de datos.
  // Ejemplo: save(), findById(), findAll(), deleteById(), entre otros.
}