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
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponseDTO> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(CategoriaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO listarPorCategoriaId(Integer categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + categoriaId + " não encontrado"));
        return new CategoriaResponseDTO(categoria);
    }

    public List<CategoriaResponseDTO> listarPorCategoriaTipo(Integer categoriaTipo) {
        CategoriaTipo tipo = CategoriaTipo.fromCodigo(categoriaTipo);
        List<Categoria> categorias = categoriaRepository.findByTipo(tipo);
        return categorias.stream()
                .map(CategoriaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO categoriaRequestDTO){

        Categoria categoria = modelMapper.map(categoriaRequestDTO, Categoria.class);
        Categoria categoriaSalva = this.categoriaRepository.save(categoria);
        CategoriaResponseDTO categoriaResponseDTO = modelMapper.map(categoriaSalva, CategoriaResponseDTO.class);
        return categoriaResponseDTO;
    }

    public CategoriaResponseDTO editarPorCategoriaId(Integer categoriaId, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoriaExistente = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + categoriaId + " não encontrada"));

        modelMapper.map(categoriaRequestDTO, categoriaExistente);

        Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);
        return new CategoriaResponseDTO(categoriaAtualizada);
    }

    public void deletarPorCategoriaId(Integer categoriaId){
        boolean categoriaExistente = categoriaRepository.existsById(categoriaId);
        if(categoriaExistente){
            categoriaRepository.deleteById(categoriaId);
        }
    }
}
