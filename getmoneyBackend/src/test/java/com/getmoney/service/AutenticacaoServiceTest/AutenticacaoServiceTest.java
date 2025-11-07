package com.getmoney.service.AutenticacaoServiceTest;

import com.getmoney.repository.UsuarioRepository;
import com.getmoney.service.AutenticacaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AutenticacaoService autenticacaoService;

    /**
     * Testa o cenário onde o usuário é encontrado com sucesso
     * Verifica se o método loadUserByUsername retorna os UserDetails corretamente
     * quando o email existe no banco de dados
     */
    @Test
    void deveCarregarUsuarioPorEmailComSucesso() {
        // Cenario
        String email = "usuario@teste.com";
        UserDetails userDetailsMock = mock(UserDetails.class);

        when(usuarioRepository.findByEmail(email))
                .thenReturn(userDetailsMock);

        // Execução
        UserDetails resultado = autenticacaoService.loadUserByUsername(email);

        // Valiadação
        assertNotNull(resultado);
        assertEquals(userDetailsMock, resultado);
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    /**
     * Testa o cenário onde o usuário não é encontrado
     * Verifica se o método lança UsernameNotFoundException com a mensagem correta
     * quando o email não existe no banco de dados
     */
    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Cenario
        String email = "naoexiste@teste.com";

        when(usuarioRepository.findByEmail(email))
                .thenReturn(null);

        // Execução e Validação
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> autenticacaoService.loadUserByUsername(email)
        );

        assertEquals("Usuário não encontrado: " + email, exception.getMessage());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

}