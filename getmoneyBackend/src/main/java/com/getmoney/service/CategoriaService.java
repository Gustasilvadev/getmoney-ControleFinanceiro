package com.getmoney.service;

import com.getmoney.entity.Categoria;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private CategoriaRepository categoriaRepository;

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
        // Converter o código inteiro para o enum
        CategoriaTipo tipo = CategoriaTipo.fromCodigo(categoriaTipo);

        // Buscar todas as categorias do tipo especificado
        return categoriaRepository.findByTipo(tipo);
    }

    public Categoria criarCategoria(Categoria categoria){
        return this.categoriaRepository.save(categoria);
    }

    public Categoria editarPorCategoriaId(Integer categoriaId, Categoria categoria){
        return categoriaRepository.findById(categoriaId)
                .map(categoriaExistente -> {

                    if (!categoriaExistente.getNome().equals(categoria.getNome())) {
                        categoriaExistente.setNome(categoria.getNome());
                    }
                    if (!categoriaExistente.getTipo().equals(categoria.getTipo())) {
                        categoriaExistente.setTipo(categoria.getTipo());
                    }
                    if (!categoriaExistente.getStatus().equals(categoria.getStatus())) {
                        categoriaExistente.setStatus(categoria.getStatus());
                    }
                    return categoriaRepository.save(categoriaExistente);
                })
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrado com id " + categoriaId));
    }

    public void deletarPorCategoriaId(Integer categoriaId){
        boolean categoriaExistente = categoriaRepository.existsById(categoriaId);
        if(categoriaExistente){
            categoriaRepository.deleteById(categoriaId);
        }
    }
}
