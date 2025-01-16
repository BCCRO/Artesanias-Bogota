package com.ud.login_module.repositories;

import com.ud.login_module.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
//    @EntityGraph(value = "producto.categoriaProducto", type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT p FROM Producto p")
//    List<Producto> findAllWithCategoriaPadre();

}
