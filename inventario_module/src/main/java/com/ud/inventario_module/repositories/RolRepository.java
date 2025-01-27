package com.ud.inventario_module.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.inventario_module.models.Rol;

/**
 * Repositorio para manejar operaciones CRUD y consultas personalizadas para la entidad {@link Rol}.
 * Extiende {@link JpaRepository} para proporcionar métodos estándar de persistencia.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su identificador único.
     *
     * @param id identificador del rol.
     * @return un {@link Optional} que contiene el rol si existe, o vacío en caso contrario.
     */
    @SuppressWarnings("null")
    Optional<Rol> findById(Long id);

    /**
     * Busca un rol por su nombre, ignorando diferencias en mayúsculas y minúsculas.
     *
     * @param rol nombre del rol a buscar.
     * @return un {@link Optional} que contiene el rol si existe, o vacío en caso contrario.
     */
    Optional<Rol> findByRolIgnoreCase(String rol);
}
