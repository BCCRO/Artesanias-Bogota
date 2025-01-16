package com.ud.login_module.services;

import com.ud.login_module.models.Factura;
import com.ud.login_module.models.FacturaHasProducto;
import com.ud.login_module.models.Producto;
import com.ud.login_module.models.dtos.FacturaHasProductoDTO;
import com.ud.login_module.repositories.FacturaHasProductoRepository;
import com.ud.login_module.repositories.FacturaRepository;
import com.ud.login_module.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacturaHasProductoService {

    @Autowired
    private FacturaHasProductoRepository facturaHasProductoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoHasPuntoVentaService productoHasPuntoVentaService;

    private void actualizarTotalFactura(Long idFactura, Long idProducto, int cantidad){
        Optional<Factura> facturaOpt = facturaRepository.findById(idFactura);
        Optional<Producto> productoOpt = productoRepository.findById(idProducto);

        if(facturaOpt.isEmpty()){
            throw new RuntimeException("Error actualizando la factura: " + idFactura);
            /**
             * TODO devolver respuesta en el controller
             */
        }
        else if(productoOpt.isEmpty()){
            throw new RuntimeException("Error encontrando el producto: " + idProducto);
            /**
             * TODO devolver respuesta en el controller
             */
        }
        else{
            Factura factura = facturaOpt.get();
            Producto producto = productoOpt.get();
            Long totlaAnterior = factura.getTotal();
            Long nuevoValor = (totlaAnterior + producto.getPrecioUnitario()) * cantidad;
            factura.setTotal(nuevoValor);
            double impuestos = factura.getTotal() * 0.16;
            factura.setTotalImpuesto((long) impuestos);
        }
    }

    public void anadirProductoFactura(Long idPuntoVenta, Long idFactura, Long idProducto, int cantidad){
        FacturaHasProducto facturaHasProducto = new FacturaHasProducto();
        facturaHasProducto.setIdFactura(idFactura);
        facturaHasProducto.setIdProducto(idProducto);
        facturaHasProducto.setCantidad(cantidad);
        facturaHasProducto.setIdPuntoVenta(idPuntoVenta);
        facturaHasProductoRepository.save(facturaHasProducto);

        productoHasPuntoVentaService.restarUnidadProductoPuntoVenta(idProducto, idPuntoVenta);
        actualizarTotalFactura(idFactura, idProducto, cantidad);
    }

    public void anadirProductosFactura(List<FacturaHasProductoDTO> listadoProductos){

        Iterable<FacturaHasProducto> iterable = listadoProductos.stream().map( dto -> {
                            productoHasPuntoVentaService.restarUnidadProductoPuntoVenta(dto.getIdProducto(), dto.getIdPuntoVenta());
                            actualizarTotalFactura(dto.getIdFactura(), dto.getIdProducto(), dto.getCantidad());    //Aprovechamos el bucle para actualizar el total, TODO probables problemas de rendimiento
                            return new FacturaHasProducto(dto);
                        })                                          //Mapeamos DTO a Entidad
                              .collect(Collectors.toList());      //Convertimos a iterable
        facturaHasProductoRepository.saveAll(iterable);
    }

    public boolean removeItem(Long idFactura, Long idProducto){
    if(facturaRepository.existsById(idProducto)){
      throw new IllegalArgumentException("La factura no existe",new Exception("404"));
    }
    if(!facturaHasProductoRepository.existsByIdFacturaAndIdProducto(idFactura,idProducto)){
      throw new IllegalArgumentException("El producto no corresponde a esa factura",new Exception("404"));
    }

    FacturaHasProducto factura = facturaHasProductoRepository.findByIdFacturaAndIdProducto(idFactura,idProducto).orElseThrow();
    try {
      facturaHasProductoRepository.delete(factura);
      return true;
    } catch (Exception e) {
      return false;
    }
    
    
  }

}
