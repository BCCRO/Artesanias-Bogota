package com.ud.artesanias_bogota.services;

import com.ud.artesanias_bogota.models.ProductoHasPuntoVenta;
import com.ud.artesanias_bogota.repositories.ProductoHasPuntoVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoHasPuntoVentaService {

    @Autowired
    private ProductoHasPuntoVentaRepository productoHasPuntoVentaRepository;

    public void actualizarInventario(Long idProducto, Long idPuntoVenta, int cantidad){
        Optional<ProductoHasPuntoVenta> productoHasPuntoVentaResponse = productoHasPuntoVentaRepository.findProductoHasVentaByIdProductoAndIdPuntoVenta(idProducto, idPuntoVenta);

        if(productoHasPuntoVentaResponse.isPresent()){
            ProductoHasPuntoVenta productoHasPuntoVenta = productoHasPuntoVentaResponse.get();

            productoHasPuntoVenta.setCantidad(cantidad);

            productoHasPuntoVentaRepository.save(productoHasPuntoVenta);
        }
        else throw new RuntimeException(String.format("No se encontro el producto: %s para el punto de venta: %s", idProducto, idPuntoVenta));
    }

    public void restarUnidadProductoPuntoVenta(Long idProducto, Long idPuntoVenta){

        Optional<ProductoHasPuntoVenta> productoHasPuntoVentaResponse = productoHasPuntoVentaRepository.findProductoHasVentaByIdProductoAndIdPuntoVenta(idProducto, idPuntoVenta);

        if(productoHasPuntoVentaResponse.isPresent()){
            ProductoHasPuntoVenta productoHasPuntoVenta = productoHasPuntoVentaResponse.get();
            int cantidadActual = productoHasPuntoVenta.getCantidad();
            int nuevaCantidad = cantidadActual - 1;

            productoHasPuntoVenta.setCantidad(nuevaCantidad);

            productoHasPuntoVentaRepository.save(productoHasPuntoVenta);
        }
        else throw new RuntimeException(String.format("No se encontro el producto: %s para el punto de venta: %s", idProducto, idPuntoVenta));

    }

    public List<ProductoHasPuntoVenta> getProductosHasPuntoVenta(){
        return productoHasPuntoVentaRepository.findAll();
    }

    public List<ProductoHasPuntoVenta> getProductosHasPuntoVentaByIdPuntoVenta(Long idPuntoVenta){
        return productoHasPuntoVentaRepository.findProductoHasVentaByIdPuntoVenta(idPuntoVenta);
    }

    public List<ProductoHasPuntoVenta> getProductosHasPuntoVentaByIdProducto(Long idProducto){
        return productoHasPuntoVentaRepository.findProductoHasVentaByIdProducto(idProducto);
    }

}
