package com.ud.artesanias_bogota.services;

import com.ud.artesanias_bogota.models.CategoriaProducto;
import com.ud.artesanias_bogota.repositories.CategoriaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejar la lógica de negocio relacionada con la entidad {@link CategoriaProducto}.
 * Proporciona métodos para interactuar con el repositorio {@link CategoriaProductoRepository}.
 */
@Service
public class CategoriaProductoService {

    @Autowired
    private CategoriaProductoRepository categoriaProductoRepository;

    /**
     * Recupera todas las categorías de productos almacenadas en la base de datos.
     *
     * @return una lista de todas las instancias de {@link CategoriaProducto}.
     */
    public List<CategoriaProducto> findAllCategoriaProducto() {
        return categoriaProductoRepository.findAll();
    }

    /**
     * Busca una categoría de producto específica por su identificador.
     *
     * @param idCategoriaProducto identificador único de la categoría de producto.
     * @return un {@link Optional} que contiene la categoría si existe, o vacío en caso contrario.
     */
    public Optional<CategoriaProducto> findCategoriaProductoById(Long idCategoriaProducto) {
        return categoriaProductoRepository.findById(idCategoriaProducto);
    }
}
