package com.getmoney.service;

import com.getmoney.dto.request.AlterarSenhaRequestDTO;
import com.getmoney.dto.request.UsuarioRequestDTO;
import com.getmoney.dto.response.UsuarioResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Cria o service manualmente injetando todas as dependências
        usuarioService = new UsuarioService(usuarioRepository, tokenService, modelMapper, passwordEncoder);

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("Usuário Teste");
        usuario.setEmail("usuario@teste.com");
        usuario.setSenha("senha-criptografada");
    }

    /**
     * Testa a busca do usuário logado com sucesso
     */
    @Test
    void deveListarUsuarioLogadoComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));
        when(modelMapper.map(usuario, UsuarioResponseDTO.class))
                .thenReturn(responseDTO);

        // Execucao
        UsuarioResponseDTO resultado = usuarioService.listarUsuarioLogado(usuarioId);

        // Validacao
        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(modelMapper, times(1)).map(usuario, UsuarioResponseDTO.class);
    }

    /**
     * Testa exceção quando usuário não encontrado
     */
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Cenario
        Integer usuarioId = 999;

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.empty());

        // Execucao & Validacao
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioService.listarUsuarioLogado(usuarioId)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    /**
     * Testa a edição de usuário com sucesso
     */
    @Test
    void deveEditarUsuarioComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO();
        requestDTO.setNome("Novo Nome");
        requestDTO.setEmail("novo@email.com");

        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario))
                .thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioResponseDTO.class))
                .thenReturn(responseDTO);

        // Execucao
        UsuarioResponseDTO resultado = usuarioService.editarPorUsuarioId(usuarioId, requestDTO);

        // Validacao
        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).findById(usuarioId);
        verify(usuarioRepository, times(1)).save(usuario);
        verify(modelMapper, times(1)).map(usuario, UsuarioResponseDTO.class);
    }

    /**
     * Testa exceção quando usuário não encontrado ao editar
     */
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoEditar() {
        // Cenario
        Integer usuarioId = 999;
        UsuarioRequestDTO requestDTO = new UsuarioRequestDTO();

        when(usuarioRepository.findById(usuarioId))
                .thenReturn(Optional.empty());

        // Execucao e Validacao
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> usuarioService.editarPorUsuarioId(usuarioId, requestDTO)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(usuarioRepository, times(1)).findById(usuarioId);
    }

    /**
     * Testa a alteração de senha com sucesso
     */
    @Test
    void deveAlterarSenhaComSucesso() {
        // Cenario
        String token = "Bearer jwt-token";
        AlterarSenhaRequestDTO requestDTO = new AlterarSenhaRequestDTO();
        requestDTO.setSenhaAtual("senha-antiga");
        requestDTO.setNovaSenha("nova-senha");

        when(tokenService.extrairEmailToken("jwt-token"))
                .thenReturn("usuario@teste.com");
        when(usuarioRepository.findUsuarioByEmail("usuario@teste.com"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha-antiga", "senha-criptografada"))
                .thenReturn(true);
        when(passwordEncoder.matches("nova-senha", "senha-criptografada"))
                .thenReturn(false);
        when(passwordEncoder.encode("nova-senha"))
                .thenReturn("nova-senha-criptografada");

        // Execucao
        usuarioService.alterarSenha(token, requestDTO);

        // Validacao
        verify(tokenService, times(1)).extrairEmailToken("jwt-token");
        verify(usuarioRepository, times(1)).findUsuarioByEmail("usuario@teste.com");
        verify(passwordEncoder, times(1)).matches("senha-antiga", "senha-criptografada");
        verify(passwordEncoder, times(1)).matches("nova-senha", "senha-criptografada");
        verify(passwordEncoder, times(1)).encode("nova-senha");
        verify(usuarioRepository, times(1)).save(usuario);
        assertEquals("nova-senha-criptografada", usuario.getSenha());
    }

    /**
     * Testa exceção quando senha atual está incorreta
     */
    @Test
    void deveLancarExcecaoQuandoSenhaAtualIncorreta() {
        // Cenario
        String token = "Bearer jwt-token";
        AlterarSenhaRequestDTO requestDTO = new AlterarSenhaRequestDTO();
        requestDTO.setSenhaAtual("senha-errada");
        requestDTO.setNovaSenha("nova-senha");

        when(tokenService.extrairEmailToken("jwt-token"))
                .thenReturn("usuario@teste.com");
        when(usuarioRepository.findUsuarioByEmail("usuario@teste.com"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha-errada", "senha-criptografada"))
                .thenReturn(false);

        // Execucao e Validacao
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioService.alterarSenha(token, requestDTO)
        );

        assertEquals("Senha atual incorreta", exception.getMessage());
        verify(tokenService, times(1)).extrairEmailToken("jwt-token");
        verify(usuarioRepository, times(1)).findUsuarioByEmail("usuario@teste.com");
        verify(passwordEncoder, times(1)).matches("senha-errada", "senha-criptografada");
    }

    /**
     * Testa exceção quando nova senha é igual à atual
     */
    @Test
    void deveLancarExcecaoQuandoNovaSenhaIgualAtual() {
        // Cenario
        String token = "Bearer jwt-token";
        AlterarSenhaRequestDTO requestDTO = new AlterarSenhaRequestDTO();
        requestDTO.setSenhaAtual("senha-antiga");
        requestDTO.setNovaSenha("nova-senha");

        when(tokenService.extrairEmailToken("jwt-token"))
                .thenReturn("usuario@teste.com");
        when(usuarioRepository.findUsuarioByEmail("usuario@teste.com"))
                .thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("senha-antiga", "senha-criptografada"))
                .thenReturn(true);
        when(passwordEncoder.matches("nova-senha", "senha-criptografada"))
                .thenReturn(true);

        // Execucao e Validacao
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> usuarioService.alterarSenha(token, requestDTO)
        );

        assertEquals("Nova senha não pode ser igual à atual", exception.getMessage());
        verify(tokenService, times(1)).extrairEmailToken("jwt-token");
        verify(usuarioRepository, times(1)).findUsuarioByEmail("usuario@teste.com");
        verify(passwordEncoder, times(2)).matches(anyString(), eq("senha-criptografada"));
    }
}