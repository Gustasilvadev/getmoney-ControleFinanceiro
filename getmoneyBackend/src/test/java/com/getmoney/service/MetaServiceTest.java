package com.getmoney.service;

import com.getmoney.dto.request.MetaRequestDTO;
import com.getmoney.dto.request.MetaRequestUpdateDTO;
import com.getmoney.dto.response.MetaResponseDTO;
import com.getmoney.entity.Meta;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.Status;
import com.getmoney.repository.MetaRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe MetaService
 */
@ExtendWith(MockitoExtension.class)
class MetaServiceTest {

    @Mock
    private MetaRepository metaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MetaService metaService;

    private Usuario usuario;
    private Meta meta;

    /**
     * Configuração inicial para os testes
     */
    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Usuário Teste");

        meta = new Meta();
        meta.setId(1);
        meta.setNome("Viagem para Europa");
        meta.setValorAlvo(new BigDecimal("10000.00"));
        meta.setData(LocalDate.of(2024, 12, 31));
        meta.setUsuario(usuario);
        meta.setStatus(Status.ATIVO);
    }

    /**
     * Testa o cenário onde metas são listadas com sucesso para um usuário
     */
    @Test
    void deveListarMetasComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        MetaResponseDTO metaResponseDTO = new MetaResponseDTO();
        metaResponseDTO.setId(1);
        metaResponseDTO.setNome("Viagem para Europa");
        metaResponseDTO.setUsuarioId(usuarioId);

        when(metaRepository.listarMetasAtivas(usuarioId))
                .thenReturn(Arrays.asList(meta));
        when(modelMapper.map(meta, MetaResponseDTO.class))
                .thenReturn(metaResponseDTO);

        // Execucao
        List<MetaResponseDTO> resultado = metaService.listarMetas(usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Viagem para Europa", resultado.get(0).getNome());
        verify(metaRepository, times(1)).listarMetasAtivas(usuarioId);
    }

    /**
     * Testa o cenário onde nenhuma meta é encontrada para o usuário
     */
    @Test
    void deveRetornarListaVaziaQuandoNaoHaMetas() {
        // Cenario
        Integer usuarioId = 1;
        when(metaRepository.listarMetasAtivas(usuarioId))
                .thenReturn(Arrays.asList());

        // Execucao
        List<MetaResponseDTO> resultado = metaService.listarMetas(usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(metaRepository, times(1)).listarMetasAtivas(usuarioId);
    }

    /**
     * Testa o cenário onde uma meta específica é encontrada pelo ID
     */
    @Test
    void deveListarMetaPorIdComSucesso() {
        // Cenario
        Integer metaId = 1;
        Integer usuarioId = 1;
        MetaResponseDTO metaResponseDTO = new MetaResponseDTO();
        metaResponseDTO.setId(metaId);
        metaResponseDTO.setNome("Viagem para Europa");
        metaResponseDTO.setUsuarioId(usuarioId);

        when(metaRepository.findByMetaIdAndUsuarioId(metaId, usuarioId))
                .thenReturn(meta);
        when(modelMapper.map(meta, MetaResponseDTO.class))
                .thenReturn(metaResponseDTO);

        // Execucao
        MetaResponseDTO resultado = metaService.listarPorMetaId(metaId, usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertEquals(metaId, resultado.getId());
        assertEquals("Viagem para Europa", resultado.getNome());
        assertEquals(usuarioId, resultado.getUsuarioId());
        verify(metaRepository, times(1)).findByMetaIdAndUsuarioId(metaId, usuarioId);
    }

    /**
     * Testa o cenário onde uma meta não é encontrada pelo ID
     */
    @Test
    void deveLancarExcecaoQuandoMetaNaoEncontradaPorId() {
        // Cenario
        Integer metaId = 999;
        Integer usuarioId = 1;

        when(metaRepository.findByMetaIdAndUsuarioId(metaId, usuarioId))
                .thenReturn(null);

        // Validacao
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> metaService.listarPorMetaId(metaId, usuarioId)
        );

        assertEquals("Meta não encontrada com ID: " + metaId, exception.getMessage());
        verify(metaRepository, times(1)).findByMetaIdAndUsuarioId(metaId, usuarioId);
    }

    /**
     * Testa a criação de uma meta com sucesso
     */
    @Test
    void deveCriarMetaComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        MetaRequestDTO requestDTO = new MetaRequestDTO();
        requestDTO.setNome("Comprar Carro");
        requestDTO.setValorAlvo(new BigDecimal("50000.00"));
        requestDTO.setData(LocalDate.of(2024, 12, 31));

        Meta metaSalva = new Meta();
        metaSalva.setId(1);
        metaSalva.setNome("Comprar Carro");
        metaSalva.setValorAlvo(new BigDecimal("50000.00"));
        metaSalva.setData(LocalDate.of(2024, 12, 31));
        metaSalva.setUsuario(usuario);

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));
        when(metaRepository.save(any(Meta.class)))
                .thenReturn(metaSalva);

        // Execuacao
        MetaResponseDTO resultado = metaService.criarMeta(requestDTO, usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertEquals("Comprar Carro", resultado.getNome());
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(metaRepository, times(1)).save(any(Meta.class));
    }

    /**
     * Testa o cenário onde o usuário não é encontrado ao criar meta
     */
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoCriarMeta() {
        // Cenario
        Integer usuarioId = 999;
        MetaRequestDTO requestDTO = new MetaRequestDTO();
        requestDTO.setNome("Comprar Carro");

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.empty());

        // Execucao e Validacao
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> metaService.criarMeta(requestDTO, usuarioId)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    /**
     * Testa a edição de uma meta com sucesso
     */
    @Test
    void deveEditarMetaComSucesso() {
        // Cenario
        Integer metaId = 1;
        Integer usuarioId = 1;
        MetaRequestUpdateDTO requestDTO = new MetaRequestUpdateDTO();
        requestDTO.setNome("Comprar Carro Novo");
        requestDTO.setValorAlvo(new BigDecimal("60000.00"));

        Meta metaExistente = new Meta();
        metaExistente.setId(metaId);
        metaExistente.setNome("Comprar Carro");
        metaExistente.setValorAlvo(new BigDecimal("50000.00"));
        metaExistente.setUsuario(usuario);

        MetaResponseDTO responseDTO = new MetaResponseDTO();
        responseDTO.setId(metaId);
        responseDTO.setNome("Comprar Carro Novo");

        when(metaRepository.findByMetaIdAndUsuarioId(metaId, usuarioId))
                .thenReturn(metaExistente);
        when(metaRepository.save(metaExistente))
                .thenReturn(metaExistente);
        when(modelMapper.map(metaExistente, MetaResponseDTO.class))
                .thenReturn(responseDTO);

        // Execucao
        MetaResponseDTO resultado = metaService.editarPorMetaId(metaId, requestDTO, usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertEquals("Comprar Carro Novo", resultado.getNome());
        verify(metaRepository, times(1)).findByMetaIdAndUsuarioId(metaId, usuarioId);
        verify(metaRepository, times(1)).save(metaExistente);
        verify(modelMapper, times(1)).map(metaExistente, MetaResponseDTO.class);
    }

    /**
     * Testa o cenário onde uma meta não é encontrada ao tentar editar
     */
    @Test
    void deveLancarExcecaoQuandoMetaNaoEncontradaAoEditar() {
        // Cenario
        Integer metaId = 999;
        Integer usuarioId = 1;
        MetaRequestUpdateDTO requestDTO = new MetaRequestUpdateDTO();

        when(metaRepository.findByMetaIdAndUsuarioId(metaId, usuarioId))
                .thenReturn(null);

        // Execucao e Validacao
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> metaService.editarPorMetaId(metaId, requestDTO, usuarioId)
        );

        assertEquals("Meta não encontrada com ID: " + metaId, exception.getMessage());
        verify(metaRepository, times(1)).findByMetaIdAndUsuarioId(metaId, usuarioId);
    }

    /**
     * Testa a exclusão de uma meta com sucesso
     */
    @Test
    void deveDeletarMetaComSucesso() {
        // Cenario
        Integer metaId = 1;
        Integer usuarioId = 1;

        when(metaRepository.existsByIdAndUsuarioId(metaId, usuarioId))
                .thenReturn(true);
        doNothing().when(metaRepository).apagarMeta(metaId, usuarioId);

        // Execucao
        metaService.deletarPorMetaId(metaId, usuarioId);

        // Validacao
        verify(metaRepository, times(1)).existsByIdAndUsuarioId(metaId, usuarioId);
        verify(metaRepository, times(1)).apagarMeta(metaId, usuarioId);
    }

    /**
     * Testa o cenário onde uma meta não é encontrada ao tentar deletar
     */
    @Test
    void deveLancarExcecaoQuandoMetaNaoEncontradaAoDeletar() {
        // Cenario
        Integer metaId = 999;
        Integer usuarioId = 1;

        when(metaRepository.existsByIdAndUsuarioId(metaId, usuarioId))
                .thenReturn(false);

        // Validacao e Execucao
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> metaService.deletarPorMetaId(metaId, usuarioId)
        );

        assertEquals("Meta não encontrada com ID: " + metaId, exception.getMessage());
        verify(metaRepository, times(1)).existsByIdAndUsuarioId(metaId, usuarioId);
    }

    /**
     * Testa o cenário onde uma meta tem transações associadas
     */
    @Test
    void deveListarMetaComTransacoesComSucesso() {
        // Cenario
        Integer metaId = 1;
        Integer usuarioId = 1;

        // Configura meta com transações
        meta.setTransacoes(new ArrayList<>()); // Simula transações vazias

        MetaResponseDTO metaResponseDTO = new MetaResponseDTO();
        metaResponseDTO.setId(metaId);
        metaResponseDTO.setNome("Viagem para Europa");
        metaResponseDTO.setUsuarioId(usuarioId);
        metaResponseDTO.setTransacoes(new ArrayList<>());

        when(metaRepository.findByMetaIdAndUsuarioId(metaId, usuarioId))
                .thenReturn(meta);
        when(modelMapper.map(meta, MetaResponseDTO.class))
                .thenReturn(metaResponseDTO);

        // Execucao
        MetaResponseDTO resultado = metaService.listarPorMetaId(metaId, usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertNotNull(resultado.getTransacoes());
        verify(metaRepository, times(1)).findByMetaIdAndUsuarioId(metaId, usuarioId);
    }
}