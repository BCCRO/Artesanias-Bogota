package com.ud.login_module.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.login_module.models.Producto;
import com.ud.login_module.models.dtos.ProductoDTO;
import com.ud.login_module.repositories.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public Producto createProducto(ProductoDTO productoDto){

        /**
         * Lo dejamos como String para evitarnos errores en la serializcion del jpa
         */
//        Serializamos la img base 64
//        byte[] imagenBytes;
//        String base64Data = productoDto.getImagen();
//        if(base64Data == null){
//            imagenBytes = new byte[0]; // TODO error
//        }
//        else{
//            if (base64Data.contains(",")) {
//                base64Data = base64Data.split(",")[1];
//            }
//            // Decodificar la cadena base64 en byte[]
//            imagenBytes = Base64.getDecoder().decode(base64Data);
//        }

        String base64Data = productoDto.getImagen();

        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setImagen(base64Data);
        producto.setPrecioUnitario(productoDto.getPrecioUnitario());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setCalificacion(productoDto.getCalificacion());
        producto.setIdCategoriaProducto(productoDto.getIdCategoriaProducto());

        producto.setArtistasProductosId(productoDto.getArtistasProductosId());
        producto.setColorProductosId(productoDto.getColorProductosId());
        producto.setColeccionProductosId(productoDto.getColeccionProductosId());
        producto.setOficioId(productoDto.getOficioId());


        return productoRepository.save(producto);

    }

    public Optional<Producto> findProductoById(Long idProducto){
        return productoRepository.findById(idProducto);
    }

    public List<Producto> findAllProducto(){
      System.out.println("Paso algo raro");
        List<Producto> productos = productoRepository.findAll();

//        List<ProductoCategoriaProductoDTO> productosDTO = productos.stream().map(ProductoCategoriaProductoDTO::new).collect(Collectors.toList());

        return productos;
//        return productoRepository.findAllWithCategoriaPadre();
    }

    public Producto updateProducto(Long id, ProductoDTO newProducto) throws Exception{
      Producto producto = productoRepository.findById(id).orElseThrow(()->new RuntimeException("product not found"));

      if (newProducto.getNombre() != null) {
        producto.setNombre(newProducto.getNombre());
      }
      if (newProducto.getImagen() != null) {
        producto.setImagen(newProducto.getImagen());
      }
      if (newProducto.getPrecioUnitario() != null) {
        producto.setPrecioUnitario(newProducto.getPrecioUnitario());
      }
      if (newProducto.getDescripcion() != null) {
        producto.setDescripcion(newProducto.getDescripcion());
      }
      if (newProducto.getCalificacion() != 0) {
        producto.setCalificacion(newProducto.getCalificacion());
      }
      if (newProducto.getIdCategoriaProducto() != 0) {
        producto.setIdCategoriaProducto(newProducto.getIdCategoriaProducto());
      }
      if (newProducto.getColorProductosId() != 0) {
        producto.setColorProductosId(newProducto.getColorProductosId());
      }
      if (newProducto.getOficioId() != 0) {
        producto.setOficioId(newProducto.getOficioId());;
      }
      if (newProducto.getColeccionProductosId() != 0) {
        producto.setColeccionProductosId(newProducto.getColeccionProductosId());
      }
      if (newProducto.getArtistasProductosId() != 0) {
        producto.setArtistasProductosId(newProducto.getArtistasProductosId());;
      }
            productoRepository.save(producto);
            return producto;

    }

    public boolean deactivate(Long id, String estado) throws Exception{
      Producto producto = productoRepository.findById(id).orElseThrow(()->new RuntimeException("No encontrado"));
      producto.setEstado(estado);
      try {
        productoRepository.save(producto);
      } catch (Exception e) {
        throw new Exception(e.getMessage());
      }
      return true;
    }

}
