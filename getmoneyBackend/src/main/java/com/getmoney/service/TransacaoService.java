package com.getmoney.service;

import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.dto.request.TransacaoUpdateRequestDTO;
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

    public List<TransacaoResponseDTO> listarTransacoes(){
        List<Transacao> transacoes = transacaoRepository.findAll();
        return transacoes.stream()
                .map(transacao -> modelMapper.map(transacao, TransacaoResponseDTO.class))
                .collect(Collectors.toList());
    }


    public TransacaoResponseDTO listarPorTransacaoId(Integer transacaoId) {
        Transacao transacao = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada com id: " + transacaoId));

        return new TransacaoResponseDTO(transacao);
    }

    public TransacaoResponseDTO criarTransacao(TransacaoRequestDTO transacaoRequestDTO) {
        Transacao transacao = new Transacao();
        transacao.setValor(transacaoRequestDTO.getValor());
        transacao.setDescricao(transacaoRequestDTO.getDescricao());
        transacao.setData(transacaoRequestDTO.getData());
        transacao.setStatus(transacaoRequestDTO.getStatus());

        Usuario usuario = usuarioRepository.findById(transacaoRequestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        transacao.setUsuario(usuario);

        Categoria categoria = categoriaRepository.findById(transacaoRequestDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
        transacao.setCategoria(categoria);

        if (transacaoRequestDTO.getMetasIds() != null && !transacaoRequestDTO.getMetasIds().isEmpty()) {
            List<Meta> metas = metaRepository.findAllById(transacaoRequestDTO.getMetasIds());
            transacao.setMetas(metas);
        }

        Transacao transacaoSalva = transacaoRepository.save(transacao);
        return new TransacaoResponseDTO(transacaoSalva);
    }


    public TransacaoResponseDTO editarPorTransacaoId(Integer transacaoId, TransacaoUpdateRequestDTO transacaoUpdateRequestDTO) {
        Transacao transacaoExistente = transacaoRepository.findById(transacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada com id " + transacaoId));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(transacaoUpdateRequestDTO, transacaoExistente);

        if (transacaoUpdateRequestDTO.getMetasIds() != null) {
            if (transacaoUpdateRequestDTO.getMetasIds().isEmpty()) {
                transacaoExistente.setMetas(new ArrayList<>()); // Remove todas as metas
            } else {
                List<Meta> metas = metaRepository.findAllById(transacaoUpdateRequestDTO.getMetasIds());
                transacaoExistente.setMetas(metas);
            }
        }
        // Salvar a transação atualizada
        Transacao transacaoAtualizada = transacaoRepository.save(transacaoExistente);

        return modelMapper.map(transacaoAtualizada, TransacaoResponseDTO.class);
    }

    public void deletarPorTransacaoId(Integer transacaoId) {
        boolean transacaoExistente = transacaoRepository.existsById(transacaoId);
        if(transacaoExistente){
            transacaoRepository.deleteById(transacaoId);
        }
    }
}
