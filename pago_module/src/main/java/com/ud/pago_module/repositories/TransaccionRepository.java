package com.ud.pago_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ud.pago_module.models.Transaccion;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar operaciones CRUD en la entidad {@link Transaccion}.
 * Extiende {@link JpaRepository} para proporcionar métodos básicos de persistencia
 * y consultas personalizadas si es necesario.
 */
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    /**
     * Busca una transacción por su identificador único.
     *
     * @param id El identificador de la transacción.
     * @return Un {@link Optional} que contiene la transacción si existe, de lo contrario está vacío.
     */
    @SuppressWarnings("null")
    Optional<Transaccion> findById(Long id);

    /**
     * Recupera todas las transacciones almacenadas en la base de datos.
     *
     * @return Una lista de todas las transacciones.
     */
    @SuppressWarnings("null")
    List<Transaccion> findAll();
}