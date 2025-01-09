package com.ud.pago_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.CategoriaProducto;

import java.util.List;


@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {
  List<CategoriaProducto> findById(int id);

}
