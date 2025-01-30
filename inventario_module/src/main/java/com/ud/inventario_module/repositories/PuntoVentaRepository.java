package com.ud.inventario_module.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ud.inventario_module.models.PuntoVenta;

@Repository
public interface PuntoVentaRepository extends JpaRepository<PuntoVenta, Long>{
  
}
