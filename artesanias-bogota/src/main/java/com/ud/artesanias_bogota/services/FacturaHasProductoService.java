package com.ud.artesanias_bogota.services;

import com.ud.artesanias_bogota.models.Factura;
import com.ud.artesanias_bogota.models.FacturaHasProducto;
import com.ud.artesanias_bogota.models.Producto;
import com.ud.artesanias_bogota.models.dtos.FacturaHasProductoDTO;
import com.ud.artesanias_bogota.repositories.FacturaHasProductoRepository;
import com.ud.artesanias_bogota.repositories.FacturaRepository;
import com.ud.artesanias_bogota.repositories.ProductoRepository;
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

    public void anadirProductoFactura(Long idFactura, Long idProducto, int cantidad){
        FacturaHasProducto facturaHasProducto = new FacturaHasProducto();
        facturaHasProducto.setIdFactura(idFactura);
        facturaHasProducto.setIdProducto(idProducto);
        facturaHasProducto.setCantidad(cantidad);
        facturaHasProductoRepository.save(facturaHasProducto);

        actualizarTotalFactura(idFactura, idProducto, cantidad);
    }

    public void anadirProductosFactura(List<FacturaHasProductoDTO> listadoProductos){

        Iterable<FacturaHasProducto> iterable = listadoProductos.stream().map( dto -> {
                            actualizarTotalFactura(dto.getIdFactura(), dto.getIdProducto(), dto.getCantidad());    //Aprovechamos el bucle para actualizar el total, TODO probables problemas de rendimiento
                            return new FacturaHasProducto(dto);
                        })                                          //Mapeamos DTO a Entidad
                              .collect(Collectors.toList());      //Convertimos a iterable
        facturaHasProductoRepository.saveAll(iterable);
    }

}
