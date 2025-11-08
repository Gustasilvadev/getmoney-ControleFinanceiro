package com.getmoney.service;

import com.getmoney.dto.request.CategoriaRequestDTO;
import com.getmoney.dto.response.CategoriaResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;
import com.getmoney.repository.CategoriaRepository;
import com.getmoney.repository.TransacaoRepository;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe CategoriaService
 */
@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    /**
     * Testa o cenário onde categorias são listadas com sucesso para um usuário
     */
    @Test
    void deveListarCategoriasComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Alimentação");
        categoria.setStatus(Status.ATIVO);

        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO();
        responseDTO.setId(1);
        responseDTO.setNome("Alimentação");

        when(categoriaRepository.findByUsuarioIdAndStatus(usuarioId, Status.ATIVO))
                .thenReturn(Arrays.asList(categoria));
        when(modelMapper.map(categoria, CategoriaResponseDTO.class))
                .thenReturn(responseDTO);

        // Execução
        List<CategoriaResponseDTO> resultado = categoriaService.listarCategorias(usuarioId);

        // Validação
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Alimentação", resultado.get(0).getNome());
        verify(categoriaRepository, times(1)).findByUsuarioIdAndStatus(usuarioId, Status.ATIVO);
    }

    /**
     * Testa o cenário onde uma categoria específica é encontrada pelo ID
     */
    @Test
    void deveListarCategoriaPorIdComSucesso() {
        // Cenario
        Integer categoriaId = 1;
        Integer usuarioId = 1;
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setNome("Transporte");
        categoria.setStatus(Status.ATIVO);

        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO();
        responseDTO.setId(categoriaId);
        responseDTO.setNome("Transporte");

        when(categoriaRepository.findByCategoriaId(categoriaId, usuarioId))
                .thenReturn(categoria);
        when(modelMapper.map(categoria, CategoriaResponseDTO.class))
                .thenReturn(responseDTO);

        // Execução
        CategoriaResponseDTO resultado = categoriaService.listarPorCategoriaId(categoriaId, usuarioId);

        // Validação
        assertNotNull(resultado);
        assertEquals(categoriaId, resultado.getId());
        assertEquals("Transporte", resultado.getNome());
        verify(categoriaRepository, times(1)).findByCategoriaId(categoriaId, usuarioId);
    }

    /**
     * Testa o cenário onde uma categoria não é encontrada pelo ID
     */
    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontradaPorId() {
        // Cenario
        Integer categoriaId = 999;
        Integer usuarioId = 1;

        when(categoriaRepository.findByCategoriaId(categoriaId, usuarioId))
                .thenReturn(null);

        // Execução e Validação
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> categoriaService.listarPorCategoriaId(categoriaId, usuarioId)
        );

        assertEquals("Categoria não encontrada com ID: " + categoriaId, exception.getMessage());
        verify(categoriaRepository, times(1)).findByCategoriaId(categoriaId, usuarioId);
    }

    /**
     * Testa o cenário onde uma categoria é encontrada mas não está ativa
     */
    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEstaAtiva() {
        // Cenario
        Integer categoriaId = 1;
        Integer usuarioId = 1;
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        categoria.setStatus(Status.INATIVO); // Categoria inativa

        when(categoriaRepository.findByCategoriaId(categoriaId, usuarioId))
                .thenReturn(categoria);

        // Execução e Validação
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> categoriaService.listarPorCategoriaId(categoriaId, usuarioId)
        );

        assertEquals("Categoria não está ativa", exception.getMessage());
    }

    /**
     * Testa a criação de uma categoria com sucesso
     */
    @Test
    void deveCriarCategoriaComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        CategoriaRequestDTO requestDTO = new CategoriaRequestDTO();
        requestDTO.setNome("Educação");
        requestDTO.setTipo(CategoriaTipo.DESPESA);

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        Categoria categoriaSalva = new Categoria();
        categoriaSalva.setId(1);
        categoriaSalva.setNome("Educação");
        categoriaSalva.setTipo(CategoriaTipo.DESPESA);

        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO();
        responseDTO.setId(1);
        responseDTO.setNome("Educação");

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));
        when(categoriaRepository.save(any(Categoria.class)))
                .thenReturn(categoriaSalva);
        when(modelMapper.map(categoriaSalva, CategoriaResponseDTO.class))
                .thenReturn(responseDTO);

        // Execução
        CategoriaResponseDTO resultado = categoriaService.criarCategoria(requestDTO, usuarioId);

        // Validação
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Educação", resultado.getNome());
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    /**
     * Testa o cenário onde o usuário não é encontrado ao criar categoria
     */
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoCriarCategoria() {
        // Cenario
        Integer usuarioId = 999;
        CategoriaRequestDTO requestDTO = new CategoriaRequestDTO();
        requestDTO.setNome("Educação");

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.empty());

        // Execução e Validação
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> categoriaService.criarCategoria(requestDTO, usuarioId)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    /**
     * Testa a edição de uma categoria com sucesso
     */
    @Test
    void deveEditarCategoriaComSucesso() {
        // Cenario
        Integer categoriaId = 1;
        Integer usuarioId = 1;
        CategoriaRequestDTO requestDTO = new CategoriaRequestDTO();
        requestDTO.setNome("Educação Atualizada");
        requestDTO.setTipo(CategoriaTipo.RECEITA);

        Categoria categoriaExistente = new Categoria();
        categoriaExistente.setId(categoriaId);
        categoriaExistente.setNome("Educação");
        categoriaExistente.setTipo(CategoriaTipo.DESPESA);

        Categoria categoriaAtualizada = new Categoria();
        categoriaAtualizada.setId(categoriaId);
        categoriaAtualizada.setNome("Educação Atualizada");
        categoriaAtualizada.setTipo(CategoriaTipo.RECEITA);

        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO();
        responseDTO.setId(categoriaId);
        responseDTO.setNome("Educação Atualizada");

        when(categoriaRepository.findByCategoriaId(categoriaId, usuarioId))
                .thenReturn(categoriaExistente);
        when(categoriaRepository.save(categoriaExistente))
                .thenReturn(categoriaAtualizada);
        when(modelMapper.map(categoriaAtualizada, CategoriaResponseDTO.class))
                .thenReturn(responseDTO);

        // Execução
        CategoriaResponseDTO resultado = categoriaService.editarPorCategoriaId(categoriaId, requestDTO, usuarioId);

        // Validação
        assertNotNull(resultado);
        assertEquals("Educação Atualizada", resultado.getNome());
        verify(categoriaRepository, times(1)).findByCategoriaId(categoriaId, usuarioId);
        verify(categoriaRepository, times(1)).save(categoriaExistente);
    }

    /**
     * Testa a exclusão de uma categoria com sucesso
     */
    @Test
    void deveDeletarCategoriaComSucesso() {
        // Cenario
        Integer categoriaId = 1;
        Integer usuarioId = 1;
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);

        when(categoriaRepository.findByCategoriaId(categoriaId, usuarioId))
                .thenReturn(categoria);
        doNothing().when(categoriaRepository).apagarCatgoria(categoriaId, usuarioId);

        // Execução
        categoriaService.deletarPorCategoriaId(categoriaId, usuarioId);

        // Validação
        verify(categoriaRepository, times(1)).findByCategoriaId(categoriaId, usuarioId);
        verify(categoriaRepository, times(1)).apagarCatgoria(categoriaId, usuarioId);
    }

    /**
     * Testa o cenário onde uma categoria não é encontrada ao tentar deletar
     */
    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontradaAoDeletar() {
        // Cenario
        Integer categoriaId = 999;
        Integer usuarioId = 1;

        when(categoriaRepository.findByCategoriaId(categoriaId, usuarioId))
                .thenReturn(null);

        // Execução e validação
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoriaService.deletarPorCategoriaId(categoriaId, usuarioId)
        );

        assertEquals("Categoria não encontrada com ID: " + categoriaId, exception.getMessage());
        verify(categoriaRepository, times(1)).findByCategoriaId(categoriaId, usuarioId);
    }

    /**
     * Testa a busca de categorias por tipo
     */
    @Test
    void deveListarCategoriasPorTipoComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        CategoriaTipo tipo = CategoriaTipo.DESPESA;
        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setNome("Alimentação");
        categoria.setTipo(tipo);

        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO();
        responseDTO.setId(1);
        responseDTO.setNome("Alimentação");

        when(categoriaRepository.findByTipo(tipo, usuarioId))
                .thenReturn(Arrays.asList(categoria));
        when(modelMapper.map(categoria, CategoriaResponseDTO.class))
                .thenReturn(responseDTO);

        // Execução
        List<CategoriaResponseDTO> resultado = categoriaService.listarPorCategoriaTipo(tipo.getCodigo(), usuarioId);

        // Validação
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Alimentação", resultado.get(0).getNome());
        verify(categoriaRepository, times(1)).findByTipo(tipo, usuarioId);
    }

}