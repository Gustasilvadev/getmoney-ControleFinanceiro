package com.getmoney.service;

import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.dto.request.TransacaoUpdateRequestDTO;
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

    public List<TransacaoResponseDTO> listarTransacoesPorCategoria(Integer categoriaId) {
        List<Transacao> transacoes = transacaoRepository.listarTransacaoPorCategoriaId(categoriaId);
        return transacoes.stream()
                .map(transacao -> modelMapper.map(transacao, TransacaoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<TransacaoBasicaResponseDTO> listarTransacoesPorMeta(Integer metaId) {
        List<Transacao> transacoes = transacaoRepository.ListarTransacaoPorMetaId(metaId);
        return transacoes.stream()
                .map(transacao -> modelMapper.map(transacao, TransacaoBasicaResponseDTO.class))
                .collect(Collectors.toList());
    }

    public TransacaoResponseDTO obterTransacaoPorCategoria(Integer id, Integer categoriaId) {
        Transacao transacao = transacaoRepository.listarTransacaoIdECategoriaId(id, categoriaId);
        if (transacao == null) {
            throw new EntityNotFoundException("Transação não encontrada para esta categoria");
        }
        return modelMapper.map(transacao, TransacaoResponseDTO.class);
    }

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

        return new TransacaoResponseDTO(transacao);
    }
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

    @Transactional
    public TransacaoResponseDTO editarPorTransacaoId(Integer transacaoId, TransacaoUpdateRequestDTO transacaoUpdateRequestDTO) {
        Transacao transacaoExistente = transacaoRepository.ObterTransacaoPeloId(transacaoId);
        if (transacaoExistente == null) {
            throw new RuntimeException("Transacao não encontrada com ID: " + transacaoId);
        }

        // Atualiza manualmente os campos necessários
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

        // Atualiza relações (usuário e categoria)
        if (transacaoUpdateRequestDTO.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(transacaoUpdateRequestDTO.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            transacaoExistente.setUsuario(usuario);
        }
        if (transacaoUpdateRequestDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(transacaoUpdateRequestDTO.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            transacaoExistente.setCategoria(categoria);
        }

        // Atualiza metas
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
