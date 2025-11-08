package com.getmoney.service;


import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.dto.request.TransacaoUpdateRequestDTO;
import com.getmoney.dto.response.TransacaoResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Transacao;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;
import com.getmoney.repository.CategoriaRepository;
import com.getmoney.repository.MetaRepository;
import com.getmoney.repository.TransacaoRepository;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private MetaRepository metaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TransacaoService transacaoService;

    private Usuario usuario;
    private Categoria categoria;
    private Transacao transacao;

    @BeforeEach
    void setUp() {
        // Configura Usuario
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Usuário Teste");
        usuario.setEmail("usuario@teste.com");

        // Configura Categoria com Usuario associado
        categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Alimentação");
        categoria.setTipo(CategoriaTipo.DESPESA);
        categoria.setUsuario(usuario);

        // Configura Transacao completa
        transacao = new Transacao();
        transacao.setId(1);
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setDescricao("Supermercado");
        transacao.setData(LocalDate.now());
        transacao.setUsuario(usuario);
        transacao.setCategoria(categoria);
        transacao.setStatus(Status.ATIVO);
    }

    /**
     * Testa a listagem de transações ativas com sucesso
     */
    @Test
    void deveListarTransacoesAtivasComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        when(transacaoRepository.listarTransacoesAtivas(usuarioId))
                .thenReturn(Arrays.asList(transacao));

        // Execucao
        List<TransacaoResponseDTO> resultado = transacaoService.listarTransacoesAtivas(usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(transacaoRepository, times(1)).listarTransacoesAtivas(usuarioId);
    }

    /**
     * Testa a busca de transação por ID com sucesso
     */
    @Test
    void deveListarTransacaoPorIdComSucesso() {
        // Cenario
        Integer transacaoId = 1;
        Integer usuarioId = 1;

        when(transacaoRepository.findByTransacaoId(transacaoId, usuarioId))
                .thenReturn(transacao);

        // Execucao
        TransacaoResponseDTO resultado = transacaoService.listarPorTransacaoId(transacaoId, usuarioId);

        // Validacao
        assertNotNull(resultado);
        verify(transacaoRepository, times(1)).findByTransacaoId(transacaoId, usuarioId);
    }

    /**
     * Testa a exceção quando transação não é encontrada por ID
     */
    @Test
    void deveLancarExcecaoQuandoTransacaoNaoEncontradaPorId() {
        // Cenario
        Integer transacaoId = 999;
        Integer usuarioId = 1;

        when(transacaoRepository.findByTransacaoId(transacaoId, usuarioId))
                .thenReturn(null);

        // Execucao e Validacao
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transacaoService.listarPorTransacaoId(transacaoId, usuarioId)
        );

        assertEquals("Transacao não encontrada com ID: " + transacaoId, exception.getMessage());
    }

    /**
     * Testa a criação de transação com sucesso
     */
    @Test
    void deveCriarTransacaoComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        TransacaoRequestDTO requestDTO = new TransacaoRequestDTO();
        requestDTO.setValor(new BigDecimal("150.00"));
        requestDTO.setDescricao("Restaurante");
        requestDTO.setData(LocalDate.now());
        requestDTO.setCategoriaId(1);

        Transacao transacaoSalva = new Transacao();
        transacaoSalva.setId(1);
        transacaoSalva.setValor(requestDTO.getValor());
        transacaoSalva.setDescricao(requestDTO.getDescricao());
        transacaoSalva.setData(requestDTO.getData());
        transacaoSalva.setUsuario(usuario);
        transacaoSalva.setCategoria(categoria);

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));
        when(categoriaRepository.findByCategoriaId(1, usuarioId))
                .thenReturn(categoria);
        when(transacaoRepository.save(any(Transacao.class)))
                .thenReturn(transacaoSalva);

        // Execucao
        TransacaoResponseDTO resultado = transacaoService.criarTransacao(requestDTO, usuarioId);

        // Validacao
        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(categoriaRepository, times(1)).findByCategoriaId(1, usuarioId);
        verify(transacaoRepository, times(1)).save(any(Transacao.class));
    }

    /**
     * Testa exceção quando usuário não encontrado ao criar transação
     */
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoCriarTransacao() {
        // Cenario
        Integer usuarioId = 999;
        TransacaoRequestDTO requestDTO = new TransacaoRequestDTO();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.empty());

        // Execucao & Validacao
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transacaoService.criarTransacao(requestDTO, usuarioId)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    /**
     * Testa exceção quando categoria não encontrada ao criar transação
     */
    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontradaAoCriarTransacao() {
        // Cenario
        Integer usuarioId = 1;
        TransacaoRequestDTO requestDTO = new TransacaoRequestDTO();
        requestDTO.setCategoriaId(999);

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));
        when(categoriaRepository.findByCategoriaId(999, usuarioId))
                .thenReturn(null);

        // Execucao & Validacao
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transacaoService.criarTransacao(requestDTO, usuarioId)
        );

        assertEquals("Categoria não encontrada ou não pertence ao usuário", exception.getMessage());
    }

    /**
     * Testa a edição de transação com sucesso
     */
    @Test
    void deveEditarTransacaoComSucesso() {
        // Cenario
        Integer transacaoId = 1;
        Integer usuarioId = 1;
        TransacaoUpdateRequestDTO requestDTO = new TransacaoUpdateRequestDTO();
        requestDTO.setValor(new BigDecimal("200.00"));
        requestDTO.setDescricao("Supermercado Atualizado");

        Transacao transacaoAtualizada = new Transacao();
        transacaoAtualizada.setId(1);
        transacaoAtualizada.setValor(new BigDecimal("200.00"));
        transacaoAtualizada.setDescricao("Supermercado Atualizado");
        transacaoAtualizada.setData(LocalDate.now());
        transacaoAtualizada.setUsuario(usuario);
        transacaoAtualizada.setCategoria(categoria);

        TransacaoResponseDTO responseDTO = new TransacaoResponseDTO();

        when(transacaoRepository.findByTransacaoId(transacaoId, usuarioId))
                .thenReturn(transacao);
        when(transacaoRepository.save(transacao))
                .thenReturn(transacaoAtualizada);
        when(modelMapper.map(transacaoAtualizada, TransacaoResponseDTO.class))
                .thenReturn(responseDTO);

        // Execucao
        TransacaoResponseDTO resultado = transacaoService.editarPorTransacaoId(transacaoId, requestDTO, usuarioId);

        // Validacao
        assertNotNull(resultado);
        verify(transacaoRepository, times(1)).save(transacao);
        verify(modelMapper, times(1)).map(transacaoAtualizada, TransacaoResponseDTO.class);
    }

    /**
     * Testa a exclusão de transação com sucesso
     */
    @Test
    void deveDeletarTransacaoComSucesso() {
        // Cenario
        Integer transacaoId = 1;
        Integer usuarioId = 1;

        when(transacaoRepository.findByTransacaoId(transacaoId, usuarioId))
                .thenReturn(transacao);
        doNothing().when(transacaoRepository).apagarTransacao(transacaoId, usuarioId);

        // Execucao
        transacaoService.deletarPorTransacaoId(transacaoId, usuarioId);

        // Validacao
        verify(transacaoRepository, times(1)).findByTransacaoId(transacaoId, usuarioId);
        verify(transacaoRepository, times(1)).apagarTransacao(transacaoId, usuarioId);
    }

    /**
     * Testa exceção quando transação não encontrada ao deletar
     */
    @Test
    void deveLancarExcecaoQuandoTransacaoNaoEncontradaAoDeletar() {
        // Cenario
        Integer transacaoId = 999;
        Integer usuarioId = 1;

        when(transacaoRepository.findByTransacaoId(transacaoId, usuarioId))
                .thenReturn(null);

        // Execucao e Validacao
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> transacaoService.deletarPorTransacaoId(transacaoId, usuarioId)
        );

        assertEquals("Transação não encontrada com ID: " + transacaoId, exception.getMessage());
    }

    /**
     * Testa a listagem de transações por meta
     */
    @Test
    void deveListarTransacoesPorMetaComSucesso() {
        // Cenario
        Integer metaId = 1;
        Integer usuarioId = 1;

        when(transacaoRepository.ListarTransacaoPorMetaId(metaId, usuarioId))
                .thenReturn(Arrays.asList(transacao));

        // Execucao
        var resultado = transacaoService.listarTransacoesPorMeta(metaId, usuarioId);

        // Validacao
        assertNotNull(resultado);
        verify(transacaoRepository, times(1)).ListarTransacaoPorMetaId(metaId, usuarioId);
    }
}