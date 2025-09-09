package com.getmoney.service;

import com.getmoney.dto.request.CategoriaRequestDTO;
import com.getmoney.dto.response.CategoriaBasicaResponseDTO;
import com.getmoney.dto.response.CategoriaResponseDTO;
import com.getmoney.dto.response.TransacaoResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Categoria> categorias = categoriaRepository.listarCategoriasAtivas();

        return categorias.stream()
                .map(categoria -> {
                    CategoriaResponseDTO categoriaResponseDTO = modelMapper.map(categoria, CategoriaResponseDTO.class);

                    if (categoria.getTransacoes() != null) {
                        List<TransacaoResponseDTO> transacoesDTO = categoria.getTransacoes()
                                .stream()
                                .map(TransacaoResponseDTO::new)
                                .collect(Collectors.toList());
                        categoriaResponseDTO.setTransacoes(transacoesDTO);
                    } else {
                        categoriaResponseDTO.setTransacoes(new ArrayList<>());
                    }

                    return categoriaResponseDTO;
                })
                .collect(Collectors.toList());
    }

    public CategoriaResponseDTO listarPorCategoriaId(Integer categoriaId) {
        Categoria categoria = categoriaRepository.ObterCategoriaPeloId(categoriaId);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada com ID: " + categoriaId);
        }
        return new CategoriaResponseDTO(categoria);
    }

    public CategoriaBasicaResponseDTO listarTransacaoCategoriaId(Integer categoriaId) {
        Categoria categoria = categoriaRepository.ObterCategoriaPeloId(categoriaId);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada com ID: " + categoriaId);
        }
        return new CategoriaBasicaResponseDTO(categoria);
    }


    public List<CategoriaResponseDTO> listarPorCategoriaTipo(Integer categoriaTipo) {
        CategoriaTipo tipo = CategoriaTipo.fromCodigo(categoriaTipo);
        List<Categoria> categorias = categoriaRepository.findByTipo(tipo);
        return categorias.stream()
                .map(CategoriaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO categoriaRequestDTO){

        Categoria categoria = modelMapper.map(categoriaRequestDTO, Categoria.class);
        Categoria categoriaSalva = this.categoriaRepository.save(categoria);
        CategoriaResponseDTO categoriaResponseDTO = modelMapper.map(categoriaSalva, CategoriaResponseDTO.class);
        return categoriaResponseDTO;
    }
    @Transactional
    public CategoriaResponseDTO editarPorCategoriaId(Integer categoriaId, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoriaExistente = categoriaRepository.ObterCategoriaPeloId(categoriaId);
        if (categoriaExistente == null) {
            throw new RuntimeException("Categoria não encontrada com ID: " + categoriaId);
        }

        modelMapper.map(categoriaRequestDTO, categoriaExistente);

        Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);
        return new CategoriaResponseDTO(categoriaAtualizada);
    }
    @Transactional
    public void deletarPorCategoriaId(Integer categoriaId){
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new EntityNotFoundException("Categoria não encontrada com ID: " + categoriaId);
        }
        categoriaRepository.apagarCatgoria(categoriaId);
    }
}

