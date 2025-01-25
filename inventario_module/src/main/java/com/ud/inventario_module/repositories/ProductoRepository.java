package com.ud.inventario_module.repositories;

import com.ud.inventario_module.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar operaciones CRUD y consultas personalizadas para la entidad {@link Producto}.
 * Extiende {@link JpaRepository} para proporcionar métodos estándar de persistencia.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * (Comentario sobre un método futuro o inhabilitado)
     * Este método usa una entidad gráfica para cargar datos relacionados entre {@link Producto} 
     * y su categoría padre. Actualmente está deshabilitado.
     * 
     * Ejemplo de uso futuro:
     * 
     * <pre>
     * {@code
     * @EntityGraph(value = "producto.categoriaProducto", type = EntityGraph.EntityGraphType.LOAD)
     * @Query("SELECT p FROM Producto p")
     * List<Producto> findAllWithCategoriaPadre();
     * }
     * </pre>
     */
    // Método deshabilitado para recuperar productos junto con su categoría padre.
//    @EntityGraph(value = "producto.categoriaProducto", type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT p FROM Producto p")
//    List<Producto> findAllWithCategoriaPadre();
}