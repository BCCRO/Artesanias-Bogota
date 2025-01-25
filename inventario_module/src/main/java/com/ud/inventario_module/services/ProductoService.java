package com.ud.inventario_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ud.inventario_module.models.Producto;
import com.ud.inventario_module.models.dtos.ProductoDTO;
import com.ud.inventario_module.repositories.ProductoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de productos.
 * Proporciona métodos para crear, actualizar, consultar y desactivar productos.
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Crea un nuevo producto a partir de un DTO.
     *
     * @param productoDto el objeto DTO con los datos del producto a crear.
     * @return el producto creado.
     */
    public Producto createProducto(ProductoDTO productoDto) {
        // Serialización de imagen (actualmente manejado como String Base64)
        String base64Data = productoDto.getImagen();

        Producto producto = new Producto();
        producto.setNombre(productoDto.getNombre());
        producto.setImagen(base64Data);
        producto.setPrecioUnitario(productoDto.getPrecioUnitario());
        producto.setDescripcion(productoDto.getDescripcion());
        producto.setCalificacion(productoDto.getCalificacion());
        producto.setIdCategoriaProducto(productoDto.getIdCategoriaProducto());
//        producto.setArtistasProductosId(productoDto.getArtistasProductosId());
//        producto.setColorProductosId(productoDto.getColorProductosId());
//        producto.setColeccionProductosId(productoDto.getColeccionProductosId());
//        producto.setOficioId(productoDto.getOficioId());

        return productoRepository.save(producto);
    }

    /**
     * Busca un producto por su ID.
     *
     * @param idProducto el identificador del producto.
     * @return un {@link Optional} con el producto, si existe.
     */
    public Optional<Producto> findProductoById(Long idProducto) {
        return productoRepository.findById(idProducto);
    }

    /**
     * Obtiene una lista de todos los productos.
     *
     * @return lista de productos.
     */
    public List<Producto> findAllProducto() {
        return productoRepository.findAll();
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id          el identificador del producto a actualizar.
     * @param newProducto un objeto DTO con los nuevos datos del producto.
     * @return el producto actualizado.
     * @throws Exception si no se encuentra el producto.
     */
    public Producto updateProducto(Long id, ProductoDTO newProducto) throws Exception {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (newProducto.getNombre() != null) producto.setNombre(newProducto.getNombre());
        if (newProducto.getImagen() != null) producto.setImagen(newProducto.getImagen());
        if (newProducto.getPrecioUnitario() != null) producto.setPrecioUnitario(newProducto.getPrecioUnitario());
        if (newProducto.getDescripcion() != null) producto.setDescripcion(newProducto.getDescripcion());
        if (newProducto.getCalificacion() != 0) producto.setCalificacion(newProducto.getCalificacion());
        if (newProducto.getIdCategoriaProducto() != 0) producto.setIdCategoriaProducto(newProducto.getIdCategoriaProducto());
//        if (newProducto.getColorProductosId() != 0) producto.setColorProductosId(newProducto.getColorProductosId());
//        if (newProducto.getOficioId() != 0) producto.setOficioId(newProducto.getOficioId());
//        if (newProducto.getColeccionProductosId() != 0) producto.setColeccionProductosId(newProducto.getColeccionProductosId());
//        if (newProducto.getArtistasProductosId() != 0) producto.setArtistasProductosId(newProducto.getArtistasProductosId());

        productoRepository.save(producto);
        return producto;
    }

    /**
     * Cambia el estado de un producto, lo que equivale a desactivarlo o reactivarlo.
     *
     * @param id     el identificador del producto.
     * @param estado el nuevo estado del producto.
     * @return {@code true} si el cambio de estado fue exitoso.
     * @throws Exception si no se encuentra el producto o si ocurre un error al guardar.
     */
    public boolean deactivate(Long id, String estado) throws Exception {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setEstado(estado);
        try {
            productoRepository.save(producto);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return true;
    }
}