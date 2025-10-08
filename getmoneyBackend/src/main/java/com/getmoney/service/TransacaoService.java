package com.getmoney.service;

import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.dto.request.TransacaoUpdateRequestDTO;
import com.getmoney.dto.response.CategoriaBasicaResponseDTO;
import com.getmoney.dto.response.MetaBasicaResponseDTO;
import com.getmoney.dto.response.TransacaoBasicaResponseDTO;
import com.getmoney.dto.response.TransacaoResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Meta;
import com.getmoney.entity.Transacao;
import com.getmoney.entity.Usuario;
import com.getmoney.repository.CategoriaRepository;
import com.getmoney.repository.MetaRepository;
import com.getmoney.repository.TransacaoRepository;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    public List<TransacaoResponseDTO> listarTransacoesAtivas(Integer usuarioId) {
        List<Transacao> transacoes = transacaoRepository.listarTransacoesAtivas(usuarioId);

        return transacoes.stream()
                .map(transacao -> new TransacaoResponseDTO(transacao))
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as transações associadas a uma meta específica
     */
    public List<TransacaoBasicaResponseDTO> listarTransacoesPorMeta(Integer metaId, Integer usuarioId) {
        List<Transacao> transacoes = transacaoRepository.ListarTransacaoPorMetaId(metaId, usuarioId);
        return transacoes.stream()
                .map(transacao -> modelMapper.map(transacao, TransacaoBasicaResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtém uma transação específica pelo ID e ID da categoria associada
     * Lança exceção se a transação não for encontrada para a categoria especificada
     */
    public TransacaoBasicaResponseDTO obterTransacaoPorCategoria(Integer transacaoId, Integer categoriaId, Integer usuarioId) {
        Transacao transacao = transacaoRepository.listarTransacaoIdECategoriaId(transacaoId, categoriaId, usuarioId);
        if (transacao == null) {
            throw new EntityNotFoundException("Transação não encontrada para esta categoria");
        }
        return modelMapper.map(transacao, TransacaoBasicaResponseDTO.class);
    }

    /**
     * Obtém uma transação específica pelo ID e ID da meta associada
     * Lança exceção se a transação não for encontrada para a meta especificada
     */
    public TransacaoResponseDTO obterTransacaoPorMeta(Integer id, Integer metaId, Integer usuarioId) {
        Transacao transacao = transacaoRepository.listarTransacaoIdEMetaId(id, metaId, usuarioId);
        if (transacao == null) {
            throw new EntityNotFoundException("Transação não encontrada para esta meta");
        }

        return new TransacaoResponseDTO(transacao);
    }


    public TransacaoResponseDTO listarPorTransacaoId(Integer transacaoId, Integer usuarioId) {
        Transacao transacao = transacaoRepository.findByTransacaoId(transacaoId, usuarioId);
        if (transacao == null) {
            throw new RuntimeException("Transacao não encontrada com ID: " + transacaoId);
        }
        return new TransacaoResponseDTO(transacao);
    }


    /**
     * Cria uma nova transação com base nos dados fornecidos
     * Associando-a a um usuário, categoria e metas (se especificadas)
     */
    @Transactional
    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO transacaoRequestDTO, Integer usuarioId) {
        Transacao transacao = new Transacao();
        transacao.setValor(transacaoRequestDTO.getValor());
        transacao.setDescricao(transacaoRequestDTO.getDescricao());
        transacao.setData(transacaoRequestDTO.getData());

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        transacao.setUsuario(usuario);

        Categoria categoria = categoriaRepository.findByCategoriaId(transacaoRequestDTO.getCategoriaId(), usuarioId);
        if (categoria == null) {
            throw new RuntimeException("Categoria não encontrada ou não pertence ao usuário");
        }
        transacao.setCategoria(categoria);

        if (transacaoRequestDTO.getMetaId() != null && !transacaoRequestDTO.getMetaId().isEmpty()) {

            List<Meta> metas = metaRepository.findByIdInAndUsuarioId(transacaoRequestDTO.getMetaId(), usuarioId);
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
    public TransacaoResponseDTO editarPorTransacaoId(Integer transacaoId, TransacaoUpdateRequestDTO transacaoUpdateRequestDTO, Integer usuarioId) {
        Transacao transacaoExistente = transacaoRepository.findByTransacaoId(transacaoId, usuarioId);
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


        if (transacaoUpdateRequestDTO.getCategoriaId() != null) {

            Categoria categoria = categoriaRepository.findByCategoriaId(transacaoUpdateRequestDTO.getCategoriaId(), usuarioId);
            if (categoria == null) {
                throw new RuntimeException("Categoria não encontrada ou não pertence ao usuário");
            }
            transacaoExistente.setCategoria(categoria);
        }

        if (transacaoUpdateRequestDTO.getMetaId() != null) {
            if (transacaoUpdateRequestDTO.getMetaId().isEmpty()) {
                transacaoExistente.setMetas(new ArrayList<>());
            } else {

                List<Meta> metas = metaRepository.findByIdInAndUsuarioId(transacaoUpdateRequestDTO.getMetaId(), usuarioId);
                transacaoExistente.setMetas(metas);
            }
        }

        Transacao transacaoAtualizada = transacaoRepository.save(transacaoExistente);
        return modelMapper.map(transacaoAtualizada, TransacaoResponseDTO.class);
    }


    @Transactional
    public void deletarPorTransacaoId(Integer transacaoId, Integer usuarioId) {
        Transacao transacao = transacaoRepository.findByTransacaoId(transacaoId, usuarioId);
        if (transacao == null) {
            throw new EntityNotFoundException("Transação não encontrada com ID: " + transacaoId);
        }
        transacaoRepository.apagarTransacao(transacaoId, usuarioId);
    }

}
