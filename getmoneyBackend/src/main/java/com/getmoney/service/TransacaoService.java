package com.getmoney.service;

import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Transacao;
import com.getmoney.entity.Usuario;
import com.getmoney.repository.CategoriaRepository;
import com.getmoney.repository.TransacaoRepository;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<Transacao> listarTransacoes(){
        return this.transacaoRepository.findAll();
    }

    public Transacao listarPorTransaccaoId(Integer transacaoId) {
        return transacaoRepository.findById(transacaoId).orElseThrow(() -> new EntityNotFoundException("Transacao com ID " + transacaoId + " não encontrado"));
    }

    public Transacao criarTransacao(TransacaoRequestDTO requestDTO) {

        // Criar nova entidade Transacao - Copia dados do DTO para Entidade
        Transacao transacao = new Transacao();
        transacao.setValor(requestDTO.getValor());
        transacao.setDescricao(requestDTO.getDescricao());
        transacao.setData(requestDTO.getData());
        transacao.setStatus(requestDTO.getStatus());

        //Buscar usuário pelo ID recebido no DTO
        Usuario usuario = usuarioRepository.findById(requestDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        transacao.setUsuario(usuario); // Associa o objeto usuario completo à transação

        //Buscar categoria pelo ID
        Categoria categoria = categoriaRepository.findById(requestDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrado"));
        transacao.setCategoria(categoria); // Associa o objeto categoria completo à transação

        // Salvar a entidade
        return transacaoRepository.save(transacao);
    }

    public Transacao editarPorTransacaoId(Integer transacaoId, Transacao transacao){
        return transacaoRepository.findById(transacaoId)
                .map(transacaoExistente -> {

                    if (!transacaoExistente.getValor().equals(transacao.getValor())) {
                        transacaoExistente.setValor(transacao.getValor());
                    }
                    if (!transacaoExistente.getDescricao().equals(transacao.getDescricao())) {
                        transacaoExistente.setDescricao(transacao.getDescricao());
                    }
                    if (!transacaoExistente.getData().equals(transacao.getData())) {
                        transacaoExistente.setData(transacao.getData());
                    }
                    if (!transacaoExistente.getStatus().equals(transacao.getStatus())) {
                        transacaoExistente.setStatus(transacao.getStatus());
                    }
                    return transacaoRepository.save(transacaoExistente);
                })
                .orElseThrow(() -> new EntityNotFoundException("Transacao não encontrado com id " + transacaoId));
    }

    public void deletarPorTransacaoId(Integer transacaoId) {
        boolean transacaoExistente = transacaoRepository.existsById(transacaoId);
        if(transacaoExistente){
            transacaoRepository.deleteById(transacaoId);
        }
    }
}
