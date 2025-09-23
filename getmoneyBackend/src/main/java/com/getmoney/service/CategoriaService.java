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

    /**
     * Lista todas as categorias ativas, com suas transações associadas
     * Se uma categoria não possuir transações, a lista de transações será vazia.
     */
    public List<CategoriaResponseDTO> listarCategorias(Integer usuarioId) {
        List<Categoria> categorias = categoriaRepository.listarCategoriasAtivasPorUsuario(usuarioId);

        return categorias.stream()
                .map(categoria -> {
                    CategoriaResponseDTO categoriaResponseDTO = modelMapper.map(categoria, CategoriaResponseDTO.class);

                    if (categoria.getTransacoes() != null) {
                        List<TransacaoPorCategoriaResponseDTO> transacoesDTO = categoria.getTransacoes()
                                .stream()
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
    public CategoriaResponseDTO listarPorCategoriaId(Integer categoriaId) {

        Categoria categoria = categoriaRepository.findByCategoriaId(categoriaId);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada com ID: " + categoriaId);
        }

        List<Transacao> transacoesAtivas = transacaoRepository.listarTransacoesAtivasPorCategoriaId(categoriaId);

        CategoriaResponseDTO categoriResponse = new CategoriaResponseDTO(categoria);
        categoriResponse.setTransacoes(transacoesAtivas.stream()
                .map(TransacaoPorCategoriaResponseDTO::new)
                .collect(Collectors.toList()));

        return categoriResponse;
    }

    /**
     * Busca uma categoria pelo ID da transação
     */
    public CategoriaBasicaResponseDTO listarTransacaoCategoriaId(Integer categoriaId) {
        Categoria categoria = categoriaRepository.findByCategoriaId(categoriaId);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada com ID: " + categoriaId);
        }
        return new CategoriaBasicaResponseDTO(categoria);
    }

    /**
     * Lista categorias por tipo
     */
    public List<CategoriaResponseDTO> listarPorCategoriaTipo(Integer categoriaTipo) {
        CategoriaTipo tipo = CategoriaTipo.fromCodigo(categoriaTipo);
        List<Categoria> categorias = categoriaRepository.findByTipo(tipo);

        // Busca todas as transações ativas uma única vez
        List<Transacao> transacoesAtivas = transacaoRepository.listarTransacoesAtivas();

        return categorias.stream()
                .map(categoria -> {
                    CategoriaResponseDTO dto = new CategoriaResponseDTO();
                    dto.setId(categoria.getId());
                    dto.setNome(categoria.getNome());
                    dto.setTipo(categoria.getTipo());
                    dto.setStatus(categoria.getStatus());

                    // Filtra as transações ativas que pertencem a esta categoria
                    dto.setTransacoes(transacoesAtivas.stream()
                            .filter(transacao -> transacao.getCategoria() != null &&
                                    transacao.getCategoria().getId().equals(categoria.getId()))
                            .map(TransacaoPorCategoriaResponseDTO::new)
                            .collect(Collectors.toList()));

                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Busca categorias por nome
     * Se o nome for nulo ou vazio, retorna lista vazia.
     */
    public List<CategoriaBasicaResponseDTO> buscarPorCategoriaNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return categoriaRepository.findByCategoriaNome(nome.trim()).stream()
                .map(CategoriaBasicaResponseDTO::new)
                .collect(Collectors.toList());
    }


    /**
     * Lista categorias utilizadas por um usuário específico, calculando o valor total
     * das transações ativas do usuário em cada categoria
     */
    public List<CategoriaValorTotalResponseDTO> listarCategoriasComValorTotal(Integer usuarioId) {

        // Busca categorias usadas pelo usuário
        List<Categoria> categorias = categoriaRepository.buscarCategoriasPorUsuarioId(usuarioId);

        return categorias.stream()
                .map(categoria -> {

                    BigDecimal valorTotal = categoria.getTransacoes().stream()
                            .filter(transacao -> transacao.getStatus() == Status.ATIVO &&
                                    transacao.getUsuario().getId().equals(usuarioId))
                            .map(Transacao::getValor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new CategoriaValorTotalResponseDTO(
                            categoria.getId(),
                            categoria.getNome(),
                            valorTotal,
                            categoria.getUsuario().getId()
                    );
                })
                .collect(Collectors.toList());
    }


    @Transactional
    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO categoriaRequestDTO) {

        Categoria categoria = new Categoria();
        categoria.setNome(categoriaRequestDTO.getNome());
        categoria.setTipo(categoriaRequestDTO.getTipo());

        Usuario usuario = usuarioRepository.findById(categoriaRequestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        categoria.setUsuario(usuario);

        if (categoriaRequestDTO.getStatus() != null) {
            try {
                Status status = Status.fromCodigo(categoriaRequestDTO.getStatus());
                categoria.setStatus(status);
            } catch (Exception e) {
                throw new IllegalArgumentException("Status inválido: " + categoriaRequestDTO.getStatus());
            }
        }

        Categoria categoriaSalva = this.categoriaRepository.save(categoria);
        CategoriaResponseDTO categoriaResponseDTO = modelMapper.map(categoriaSalva, CategoriaResponseDTO.class);

        return categoriaResponseDTO;
    }

    @Transactional
    public CategoriaResponseDTO editarPorCategoriaId(Integer categoriaId, CategoriaRequestDTO categoriaRequestDTO) {
        Categoria categoriaExistente = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com ID: " + categoriaId));

        if (categoriaRequestDTO.getNome() != null) {
            categoriaExistente.setNome(categoriaRequestDTO.getNome());
        }

        if (categoriaRequestDTO.getTipo() != null) {
            categoriaExistente.setTipo(categoriaRequestDTO.getTipo());
        }

        if (categoriaRequestDTO.getStatus() != null) {
            try {
                Status status = Status.fromCodigo(categoriaRequestDTO.getStatus());
                categoriaExistente.setStatus(status);
            } catch (Exception e) {
                throw new IllegalArgumentException("Status inválido: " + categoriaRequestDTO.getStatus());
            }
        }

        Categoria categoriaAtualizada = categoriaRepository.save(categoriaExistente);

        return modelMapper.map(categoriaAtualizada, CategoriaResponseDTO.class);
    }
    @Transactional
    public void deletarPorCategoriaId(Integer categoriaId){
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new EntityNotFoundException("Categoria não encontrada com ID: " + categoriaId);
        }
        categoriaRepository.apagarCatgoria(categoriaId);
    }
}

