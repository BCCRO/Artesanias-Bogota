package com.ud.login_module.repositories;

import com.ud.login_module.models.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {
  List<CategoriaProducto> findById(int id);

}
