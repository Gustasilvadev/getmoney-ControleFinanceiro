package com.getmoney.service;

import com.getmoney.dto.request.CategoriaRequestDTO;
import com.getmoney.dto.response.*;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Transacao;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;
import com.getmoney.repository.CategoriaRepository;
import com.getmoney.repository.TransacaoRepository;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, TransacaoRepository transacaoRepository, UsuarioRepository usuarioRepository ) {
        this.categoriaRepository = categoriaRepository;
        this.transacaoRepository = transacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<CategoriaResponseDTO> listarCategorias(Integer usuarioId) {
        List<Categoria> categorias = categoriaRepository.findByUsuarioIdAndStatus(usuarioId, Status.ATIVO);

        return categorias.stream()
                .map(categoria -> {
                    CategoriaResponseDTO categoriaResponseDTO = modelMapper.map(categoria, CategoriaResponseDTO.class);

                    if (categoria.getTransacoes() != null) {
                        List<TransacaoPorCategoriaResponseDTO> transacoesDTO = categoria.getTransacoes()
                                .stream()
                                .filter(transacao -> transacao.getStatus() == Status.ATIVO &&
                                        transacao.getUsuario().getId().equals(usuarioId))
                                .map(TransacaoPorCategoriaResponseDTO::new)
                                .collect(Collectors.toList());
                        categoriaResponseDTO.setTransacoes(transacoesDTO);
                    } else {
                        categoriaResponseDTO.setTransacoes(new ArrayList<>());
                    }

                    return categoriaResponseDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Busca uma categoria pelo ID com suas transações ativas
     */
    public CategoriaResponseDTO listarPorCategoriaId(Integer categoriaId, Integer usuarioId) {
        Categoria categoria = categoriaRepository.findByCategoriaId(categoriaId, usuarioId);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada com ID: " + categoriaId);
        }

        // Verifica se a categoria está ativa
        if (categoria.getStatus() != Status.ATIVO) {
            throw new RuntimeException("Categoria não está ativa");
        }

        CategoriaResponseDTO categoriaResponse = modelMapper.map(categoria, CategoriaResponseDTO.class);

        // Filtrar transações ativas do usuário
        if (categoria.getTransacoes() != null) {
            List<TransacaoPorCategoriaResponseDTO> transacoesDTO = categoria.getTransacoes()
                    .stream()
                    .filter(transacao -> transacao.getStatus() == Status.ATIVO &&
                            transacao.getUsuario().getId().equals(usuarioId))
                    .map(TransacaoPorCategoriaResponseDTO::new)
                    .collect(Collectors.toList());
            categoriaResponse.setTransacoes(transacoesDTO);
        }

        return categoriaResponse;
    }

    /**
     * Busca uma categoria pelo ID da transação
     */
    public CategoriaBasicaResponseDTO listarTransacaoCategoriaId(Integer categoriaId, Integer usuarioId) {
        Categoria categoria = categoriaRepository.findByCategoriaId(categoriaId, usuarioId);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada com ID: " + categoriaId);
        }

        if (!categoria.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Categoria não pertence ao usuário autenticado");
        }

        return new CategoriaBasicaResponseDTO(categoria);
    }

    /**
     * Lista categorias por tipo
     */
    public List<CategoriaResponseDTO> listarPorCategoriaTipo(Integer categoriaTipo, Integer usuarioId) {
        CategoriaTipo tipo = CategoriaTipo.fromCodigo(categoriaTipo);
        List<Categoria> categorias = categoriaRepository.findByTipo(tipo, usuarioId);

        return categorias.stream()
                .map(categoria -> {
                    CategoriaResponseDTO categoriaResponseDTO = modelMapper.map(categoria, CategoriaResponseDTO.class);

                    if (categoria.getTransacoes() != null) {
                        List<TransacaoPorCategoriaResponseDTO> transacoesDTO = categoria.getTransacoes()
                                .stream()
                                .filter(transacao -> transacao.getStatus() == Status.ATIVO &&
                                        transacao.getUsuario().getId().equals(usuarioId))
                                .map(TransacaoPorCategoriaResponseDTO::new)
                                .collect(Collectors.toList());
                        categoriaResponseDTO.setTransacoes(transacoesDTO);
                    }

                    return categoriaResponseDTO;
                })
                .collect(Collectors.toList());
    }

    /**
     * Busca categorias por nome
     * Se o nome for nulo ou vazio, retorna lista vazia.
     */
    public List<CategoriaBasicaResponseDTO> buscarPorCategoriaNome(String nome, Integer usuarioId) {
        if (nome == null || nome.trim().isEmpty()) {
            // Retorna todas as categorias ativas do usuário
            return categoriaRepository.findByUsuarioIdAndStatus(usuarioId, Status.ATIVO)
                    .stream()
                    .map(CategoriaBasicaResponseDTO::new)
                    .collect(Collectors.toList());
        }

        return categoriaRepository.findByCategoriaNome(nome.trim(), usuarioId)
                .stream()
                .map(CategoriaBasicaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO categoriaRequestDTO, Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequestDTO.getNome());
        categoria.setTipo(categoriaRequestDTO.getTipo());
        categoria.setUsuario(usuario);

        Categoria categoriaSalva = this.categoriaRepository.save(categoria);
        return modelMapper.map(categoriaSalva, CategoriaResponseDTO.class);
    }

    @Transactional
    public CategoriaResponseDTO editarPorCategoriaId(Integer categoriaId, CategoriaRequestDTO categoriaRequestDTO, Integer usuarioId) {
        Categoria categoriaExistente = categoriaRepository.findByCategoriaId(categoriaId, usuarioId);
        if (categoriaExistente == null) {
            throw new EntityNotFoundException("Categoria não encontrada com ID: " + categoriaId);
        }

        if (categoriaRequestDTO.getNome() != null) {
            categoriaExistente.setNome(categoriaRequestDTO.getNome());
        }

        if (categoriaRequestDTO.getTipo() != null) {
            categoriaExistente.setTipo(categoriaRequestDTO.getTipo());
        }

        Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);
        return modelMapper.map(categoriaAtualizada, CategoriaResponseDTO.class);
    }

    @Transactional
    public void deletarPorCategoriaId(Integer categoriaId, Integer usuarioId) {
        Categoria categoria = categoriaRepository.findByCategoriaId(categoriaId, usuarioId);
        if (categoria == null) {
            throw new EntityNotFoundException("Categoria não encontrada com ID: " + categoriaId);
        }

        categoriaRepository.apagarCatgoria(categoriaId, usuarioId);
    }
}

