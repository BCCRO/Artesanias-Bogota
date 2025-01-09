package com.ud.pago_module.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.FacturaHasProducto;

@Repository
public interface FacturaHasProductoRepository extends JpaRepository<FacturaHasProducto, Long> {

  boolean existsByIdFacturaAndIdProducto(Long idFactura,Long idProducto);

  Optional<FacturaHasProducto> findByIdFacturaAndIdProducto(Long idFactura,Long idProducto);

}
