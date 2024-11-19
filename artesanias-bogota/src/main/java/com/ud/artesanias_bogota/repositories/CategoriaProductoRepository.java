package com.ud.artesanias_bogota.repositories;

import com.ud.artesanias_bogota.models.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {
  List<CategoriaProducto> findById(int id);

}
