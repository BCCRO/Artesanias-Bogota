package com.ud.artesanias_bogota.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ud.artesanias_bogota.models.Transaccion;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para manejar operaciones CRUD y consultas personalizadas para la entidad {@link Transaccion}.
 * Extiende {@link JpaRepository} para proporcionar métodos estándar de persistencia.
 */
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    /**
     * Busca una transacción por su identificador único.
     *
     * @param id identificador de la transacción.
     * @return un {@link Optional} que contiene la transacción si existe, o vacío en caso contrario.
     */
    @SuppressWarnings("null")
    Optional<Transaccion> findById(Long id);

    /**
     * Recupera todas las transacciones almacenadas en la base de datos.
     *
     * @return una lista de todas las transacciones.
     */
    @SuppressWarnings("null")
    List<Transaccion> findAll();
}