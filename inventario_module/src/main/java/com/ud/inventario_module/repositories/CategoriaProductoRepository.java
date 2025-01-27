package com.ud.inventario_module.repositories;

import com.ud.inventario_module.models.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio para manejar operaciones CRUD y consultas personalizadas para la entidad {@link CategoriaProducto}.
 * Extiende {@link JpaRepository} para proporcionar una implementación estándar de las operaciones de persistencia.
 */
@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {

    /**
     * Busca una lista de categorías de productos por su identificador.
     * 
     * @param id identificador de la categoría de producto.
     * @return una lista de {@link CategoriaProducto} con el identificador proporcionado.
     */
    List<CategoriaProducto> findById(int id);
}