package com.ud.pago_module.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.Usuario;

/**
 * Repositorio para gestionar operaciones CRUD en la entidad {@link Usuario}.
 * Extiende {@link JpaRepository} para proporcionar métodos básicos de persistencia
 * y consultas personalizadas específicas.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    /**
     * Busca un usuario por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del usuario.
     * @return Un {@link Optional} que contiene el usuario si existe, de lo contrario está vacío.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el documento proporcionado.
     *
     * @param documento El documento del usuario.
     * @return {@code true} si el usuario existe, de lo contrario {@code false}.
     */
    boolean existsByDocumento(String documento);

    /**
     * Verifica si existe un usuario con el número de teléfono proporcionado.
     *
     * @param telefono El número de teléfono del usuario.
     * @return {@code true} si el usuario existe, de lo contrario {@code false}.
     */
    boolean existsByTelefono(Long telefono);

    /**
     * Verifica si existe un usuario con la dirección de correo electrónico proporcionada.
     *
     * @param email La dirección de correo electrónico del usuario.
     * @return {@code true} si el usuario existe, de lo contrario {@code false}.
     */
    boolean existsByEmail(String email);
}