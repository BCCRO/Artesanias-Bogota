package com.ud.artesanias_bogota.services;

import com.ud.artesanias_bogota.models.Producto;
import com.ud.artesanias_bogota.models.dtos.ProductoCategoriaProductoDTO;
import com.ud.artesanias_bogota.models.dtos.ProductoDTO;
import com.ud.artesanias_bogota.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public Producto createProducto(ProductoDTO productoDto){

        /**
         * Lo dejamos como String para evitarnos errores en la serializcion del jpa
         */
//        Serializamos la img base 64
        byte[] imagenBytes;
        String base64Data = productoDto.getImagen();
        if(base64Data == null){
            imagenBytes = new byte[0]; // TODO error
        }
        else{
            if (base64Data.contains(",")) {
                base64Data = base64Data.split(",")[1];
            }
            // Decodificar la cadena base64 en byte[]
            imagenBytes = Base64.getDecoder().decode(base64Data);
        }

        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setImagen(base64Data);
        producto.setPrecioUnitario(productoDto.getPrecioUnitario());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setCalificacion(productoDto.getCalificacion());
        producto.setIdCategoriaProducto(producto.getIdCategoriaProducto());

        return productoRepository.save(producto);

    }

    public Optional<Producto> findProductoById(Long idProducto){
        return productoRepository.findById(idProducto);
    }

    public List<Producto> findAllProducto(){

        List<Producto> productos = productoRepository.findAll();

//        List<ProductoCategoriaProductoDTO> productosDTO = productos.stream().map(ProductoCategoriaProductoDTO::new).collect(Collectors.toList());

        return productos;
//        return productoRepository.findAllWithCategoriaPadre();
    }

}
