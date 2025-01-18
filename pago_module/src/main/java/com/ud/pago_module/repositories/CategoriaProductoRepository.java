package com.ud.pago_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.CategoriaProducto;

import java.util.List;

/**
 * Repositorio para gestionar operaciones CRUD en la entidad {@link CategoriaProducto}.
 * Extiende {@link JpaRepository} para proporcionar métodos básicos de persistencia y
 * consulta personalizada.
 */
@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {

    /**
     * Encuentra las categorías de productos por su identificador.
     *
     * @param id Identificador de la categoría de producto.
     * @return Lista de categorías de producto que coincidan con el identificador.
     */
    List<CategoriaProducto> findById(int id);
}