package com.ud.pago_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

//    @EntityGraph(value = "producto.categoriaProducto", type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT p FROM Producto p")
//    List<Producto> findAllWithCategoriaPadre();

}
