package com.ud.artesanias_bogota.repositories;

import com.ud.artesanias_bogota.models.FacturaHasProducto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaHasProductoRepository extends JpaRepository<FacturaHasProducto, Long> {

  boolean existsByIdFacturaAndIdProducto(Long idFactura,Long idProducto);

  Optional<FacturaHasProducto> findByIdFacturaAndIdProducto(Long idFactura,Long idProducto);

}
