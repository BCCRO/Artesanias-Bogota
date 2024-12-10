package com.ud.artesanias_bogota.repositories;

import com.ud.artesanias_bogota.models.FacturaHasProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaHasProductoRepository extends JpaRepository<FacturaHasProducto, Long> {
}
