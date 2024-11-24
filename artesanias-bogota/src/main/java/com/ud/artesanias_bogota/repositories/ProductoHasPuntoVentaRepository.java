package com.ud.artesanias_bogota.repositories;

import com.ud.artesanias_bogota.models.ProductoHasPuntoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoHasPuntoVentaRepository extends JpaRepository<ProductoHasPuntoVenta, Long> {

    Optional<ProductoHasPuntoVenta> findProductoHasVentaByIdProductoAndIdPuntoVenta(Long idProducto, Long idPuntoVenta);

    List<ProductoHasPuntoVenta> findProductoHasVentaByIdProducto(Long idProducto);
    List<ProductoHasPuntoVenta> findProductoHasVentaByIdPuntoVenta(Long idPuntoVenta);

}
