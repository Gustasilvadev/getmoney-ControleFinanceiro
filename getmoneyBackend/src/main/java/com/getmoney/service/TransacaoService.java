package com.getmoney.service;

import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.dto.request.TransacaoUpdateRequestDTO;
import com.getmoney.dto.response.MetaBasicaResponseDTO;
import com.getmoney.dto.response.TransacaoBasicaResponseDTO;
import com.getmoney.dto.response.TransacaoResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Meta;
import com.getmoney.entity.Transacao;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.Status;
import com.getmoney.repository.CategoriaRepository;
import com.getmoney.repository.MetaRepository;
import com.getmoney.repository.TransacaoRepository;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final MetaRepository metaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TransacaoService(TransacaoRepository transacaoRepository, UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository, MetaRepository metaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.metaRepository = metaRepository;
    }

    public List<TransacaoResponseDTO> listarTransacoesAtivas(){
        List<Transacao> transacoes = transacaoRepository.listarTransacoesAtivas();
        return transacoes.stream()
                .map(transacao -> modelMapper.map(transacao, TransacaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public TransacaoResponseDTO obterTransacaoAtivaPorId(Integer id) {
        Transacao transacao = transacaoRepository.ObterTransacaoPeloId(id);
        if (transacao == null) {
            throw new EntityNotFoundException("Transação não encontrada");
        }
        return modelMapper.map(transacao, TransacaoResponseDTO.class);
    }

    /**
     * Lista todas as transações associadas a uma meta específica
     */
    public List<TransacaoBasicaResponseDTO> listarTransacoesPorMeta(Integer metaId) {
        List<Transacao> transacoes = transacaoRepository.ListarTransacaoPorMetaId(metaId);
        return transacoes.stream()
                .map(transacao -> modelMapper.map(transacao, TransacaoBasicaResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtém uma transação específica pelo ID e ID da categoria associada
     * Lança exceção se a transação não for encontrada para a categoria especificada
     */
    public TransacaoBasicaResponseDTO obterTransacaoPorCategoria(Integer transacaoId, Integer categoriaId, Integer usuarioId) {
        Transacao transacao = transacaoRepository.listarTransacaoIdECategoriaId(
                transacaoId, categoriaId, usuarioId);

        if (transacao == null) {
            throw new EntityNotFoundException("Transação não encontrada para esta categoria");
        }
        return modelMapper.map(transacao, TransacaoBasicaResponseDTO.class);
    }

    /**
     * Obtém uma transação específica pelo ID e ID da meta associada
     * Lança exceção se a transação não for encontrada para a meta especificada
     */
    public TransacaoResponseDTO obterTransacaoPorMeta(Integer id, Integer metaId) {
        Transacao transacao = transacaoRepository.listarTransacaoIdEMetaId(id, metaId);
        if (transacao == null) {
            throw new EntityNotFoundException("Transação não encontrada para esta meta");
        }
        return modelMapper.map(transacao, TransacaoResponseDTO.class);
    }


    public TransacaoResponseDTO listarPorTransacaoId(Integer transacaoId) {
        Transacao transacao = transacaoRepository.ObterTransacaoPeloId(transacaoId);
        if (transacao == null) {
            throw new RuntimeException("Transacao não encontrada com ID: " + transacaoId);
        }

        TransacaoResponseDTO dto = modelMapper.map(transacao, TransacaoResponseDTO.class);

        dto.setMetasId(transacao.getMetas() != null ?
                transacao.getMetas().stream()
                        .map(meta -> modelMapper.map(meta, MetaBasicaResponseDTO.class))
                        .collect(Collectors.toList()) :
                new ArrayList<>());

        return dto;
    }


    /**
     * Cria uma nova transação com base nos dados fornecidos
     * Associando-a a um usuário, categoria e metas (se especificadas)
     */
    @Transactional
    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO transacaoRequestDTO) {
        Transacao transacao = new Transacao();
        transacao.setValor(transacaoRequestDTO.getValor());
        transacao.setDescricao(transacaoRequestDTO.getDescricao());
        transacao.setData(transacaoRequestDTO.getData());
        Status status = Status.fromCodigo(transacaoRequestDTO.getStatus());
        transacao.setStatus(status);

        Usuario usuario = usuarioRepository.findById(transacaoRequestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        transacao.setUsuario(usuario);

        Categoria categoria = categoriaRepository.ObterCategoriaPeloId(transacaoRequestDTO.getCategoriaId());
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada");
        }
        transacao.setCategoria(categoria);

        if (transacaoRequestDTO.getMetasId() != null && !transacaoRequestDTO.getMetasId().isEmpty()) {
            List<Meta> metas = metaRepository.findAllById(transacaoRequestDTO.getMetasId());
            transacao.setMetas(metas);
        }

        Transacao transacaoSalva = transacaoRepository.save(transacao);
        return new TransacaoResponseDTO(transacaoSalva);
    }

    /**
     * Edita uma transação existente pelo ID, atualizando apenas os campos fornecidos
     * Permite alterar, categoria, metas
     */
    @Transactional
    public TransacaoResponseDTO editarPorTransacaoId(Integer transacaoId, TransacaoUpdateRequestDTO transacaoUpdateRequestDTO) {
        Transacao transacaoExistente = transacaoRepository.ObterTransacaoPeloId(transacaoId);
        if (transacaoExistente == null) {
            throw new RuntimeException("Transacao não encontrada com ID: " + transacaoId);
        }

        if (transacaoUpdateRequestDTO.getValor() != null) {
            transacaoExistente.setValor(transacaoUpdateRequestDTO.getValor());
        }
        if (transacaoUpdateRequestDTO.getDescricao() != null) {
            transacaoExistente.setDescricao(transacaoUpdateRequestDTO.getDescricao());
        }
        if (transacaoUpdateRequestDTO.getData() != null) {
            transacaoExistente.setData(transacaoUpdateRequestDTO.getData());
        }
        if (transacaoUpdateRequestDTO.getStatus() != null) {
            transacaoExistente.setStatus(Status.fromCodigo(transacaoUpdateRequestDTO.getStatus()));
        }

        if (transacaoUpdateRequestDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(transacaoUpdateRequestDTO.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            transacaoExistente.setCategoria(categoria);
        }

        if (transacaoUpdateRequestDTO.getMetasId() != null) {
            if (transacaoUpdateRequestDTO.getMetasId().isEmpty()) {
                transacaoExistente.setMetas(new ArrayList<>());
            } else {
                List<Meta> metas = metaRepository.findAllById(transacaoUpdateRequestDTO.getMetasId());
                transacaoExistente.setMetas(metas);
            }
        }

        Transacao transacaoAtualizada = transacaoRepository.save(transacaoExistente);
        return modelMapper.map(transacaoAtualizada, TransacaoResponseDTO.class);
    }

    @Transactional
    public void deletarPorTransacaoId(Integer transacaoId) {
        boolean transacaoExistente = transacaoRepository.existsById(transacaoId);
        if(transacaoExistente){
            transacaoRepository.apagarTransacao(transacaoId);
        }
    }
}
