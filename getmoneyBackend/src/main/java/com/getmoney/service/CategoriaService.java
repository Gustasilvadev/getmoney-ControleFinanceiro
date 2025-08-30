package com.getmoney.service;

import com.getmoney.dto.request.CategoriaRequestDTO;
import com.getmoney.dto.response.CategoriaResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias(){
        return  this.categoriaRepository.findAll();
    }

    public Categoria listarPorCategoriaId(Integer categoriaId) {
        return categoriaRepository.findById(categoriaId).orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + categoriaId + " não encontrado"));
    }

    public List<Categoria> listarPorCategoriaTipo(Integer categoriaTipo) {
        CategoriaTipo tipo = CategoriaTipo.fromCodigo(categoriaTipo);
        return categoriaRepository.findByTipo(tipo);
    }

    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO categoriaRequestDTO){

        Categoria categoria = modelMapper.map(categoriaRequestDTO, Categoria.class);
        Categoria categoriaSalva = this.categoriaRepository.save(categoria);
        CategoriaResponseDTO categoriaResponseDTO = modelMapper.map(categoriaSalva, CategoriaResponseDTO.class);
        return categoriaResponseDTO;
    }

    public CategoriaResponseDTO atualizarCategoria(Integer categoriaId, CategoriaRequestDTO categoriaRequestDTO) {

        Categoria categoriaBuscada = this.listarPorCategoriaId(categoriaId);

        if (categoriaBuscada != null) {
            modelMapper.map(categoriaRequestDTO, categoriaBuscada);
            Categoria categoriaSalva = categoriaRepository.save(categoriaBuscada);
            return modelMapper.map(categoriaSalva, CategoriaResponseDTO.class);
        } else {
            return null;
        }

    }

    public void deletarPorCategoriaId(Integer categoriaId){
        boolean categoriaExistente = categoriaRepository.existsById(categoriaId);
        if(categoriaExistente){
            categoriaRepository.deleteById(categoriaId);
        }
    }
}
