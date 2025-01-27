package com.ud.pago_module.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.Rol;

/**
 * Repositorio para gestionar operaciones CRUD en la entidad {@link Rol}.
 * Extiende {@link JpaRepository} para proporcionar métodos básicos de persistencia y
 * consultas personalizadas.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su identificador único.
     *
     * @param id El identificador del rol.
     * @return Un {@link Optional} que contiene el rol si existe, de lo contrario está vacío.
     */
    @SuppressWarnings("null")
    Optional<Rol> findById(Long id);

    /**
     * Busca un rol por su nombre, ignorando mayúsculas y minúsculas.
     *
     * @param rol El nombre del rol.
     * @return Un {@link Optional} que contiene el rol si existe, de lo contrario está vacío.
     */
    Optional<Rol> findByRolIgnoreCase(String rol);
}