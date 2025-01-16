package com.ud.artesanias_bogota.repositories;

import com.ud.artesanias_bogota.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar operaciones CRUD y consultas personalizadas para la entidad {@link Factura}.
 * Extiende {@link JpaRepository} para proporcionar métodos estándar de persistencia.
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    // Esta interfaz hereda los métodos CRUD de JpaRepository, incluyendo:
    // - save(): Guarda o actualiza una entidad.
    // - findById(): Busca una entidad por su ID.
    // - findAll(): Recupera todas las entidades.
    // - deleteById(): Elimina una entidad por su ID.
    // - count(): Cuenta el número de entidades.
}