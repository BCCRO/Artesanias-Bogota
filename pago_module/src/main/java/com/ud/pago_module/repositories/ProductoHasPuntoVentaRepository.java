package com.ud.pago_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.pago_module.models.ProductoHasPuntoVenta;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoHasPuntoVentaRepository extends JpaRepository<ProductoHasPuntoVenta, Long> {

    Optional<ProductoHasPuntoVenta> findProductoHasVentaByIdProductoAndIdPuntoVenta(Long idProducto, Long idPuntoVenta);

    List<ProductoHasPuntoVenta> findProductoHasVentaByIdProducto(Long idProducto);
    List<ProductoHasPuntoVenta> findProductoHasVentaByIdPuntoVenta(Long idPuntoVenta);

}
