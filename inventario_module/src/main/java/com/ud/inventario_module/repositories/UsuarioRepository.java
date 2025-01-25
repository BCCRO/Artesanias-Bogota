package com.ud.artesanias_bogota.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ud.artesanias_bogota.models.Usuario;

/**
 * Repositorio para manejar operaciones CRUD y consultas personalizadas para la entidad {@link Usuario}.
 * Extiende {@link JpaRepository} para proporcionar métodos estándar de persistencia.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email dirección de correo electrónico del usuario.
     * @return un {@link Optional} que contiene el usuario si existe, o vacío en caso contrario.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el documento especificado.
     *
     * @param documento identificador único del usuario.
     * @return true si existe un usuario con el documento, false en caso contrario.
     */
    boolean existsByDocumento(String documento);

    /**
     * Verifica si existe un usuario con el número de teléfono especificado.
     *
     * @param telefono número de teléfono del usuario.
     * @return true si existe un usuario con el teléfono, false en caso contrario.
     */
    boolean existsByTelefono(Long telefono);

    /**
     * Verifica si existe un usuario con el correo electrónico especificado.
     *
     * @param email dirección de correo electrónico del usuario.
     * @return true si existe un usuario con el email, false en caso contrario.
     */
    boolean existsByEmail(String email);
}