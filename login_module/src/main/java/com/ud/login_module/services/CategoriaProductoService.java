package com.ud.login_module.services;

import com.ud.login_module.models.CategoriaProducto;
import com.ud.login_module.repositories.CategoriaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaProductoService {

    @Autowired
    CategoriaProductoRepository categoriaProductoRepository;

    public List<CategoriaProducto> findAllCategoriaProducto(){
        return categoriaProductoRepository.findAll();
    }

    public Optional<CategoriaProducto> findCategoriaProductoById(Long idCategoriaProducto){
        return categoriaProductoRepository.findById(idCategoriaProducto);
    }

}
