package com.ud.pago_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.Producto;

/**
 * Repositorio para gestionar operaciones CRUD en la entidad {@link Producto}.
 * Extiende {@link JpaRepository} para proporcionar métodos básicos de persistencia
 * y está preparado para agregar consultas personalizadas si es necesario.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * (Comentado actualmente) Método para obtener todos los productos con sus categorías padre.
     * Este método utiliza una entidad gráfica para cargar las relaciones relacionadas de forma eficiente.
     *
     * Ejemplo de uso:
     * <pre>
     * {@code
     * @EntityGraph(value = "producto.categoriaProducto", type = EntityGraph.EntityGraphType.LOAD)
     * @Query("SELECT p FROM Producto p")
     * List<Producto> findAllWithCategoriaPadre();
     * }
     * </pre>
     */
    // Código comentado para la consulta con EntityGraph.
}