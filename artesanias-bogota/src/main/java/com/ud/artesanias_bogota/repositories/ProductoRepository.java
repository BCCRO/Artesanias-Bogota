package com.ud.artesanias_bogota.repositories;

import com.ud.artesanias_bogota.models.Producto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

//    @EntityGraph(value = "producto.categoriaProducto", type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT p FROM Producto p")
//    List<Producto> findAllWithCategoriaPadre();

}
