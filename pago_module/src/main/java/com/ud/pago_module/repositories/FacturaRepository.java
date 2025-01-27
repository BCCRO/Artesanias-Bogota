package com.ud.pago_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.Factura;

/**
 * Repositorio para gestionar operaciones CRUD en la entidad {@link Factura}.
 * Extiende {@link JpaRepository} para proporcionar métodos básicos de persistencia.
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    // La interfaz JpaRepository ya proporciona métodos básicos como save, findById, deleteById, etc.
}