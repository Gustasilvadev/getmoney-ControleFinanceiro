package com.getmoney.controller;

import com.getmoney.dto.request.LoginRequestDTO;
import com.getmoney.dto.request.RegistroRequestDTO;
import com.getmoney.dto.response.LoginResponseDTO;
import com.getmoney.dto.response.UsuarioResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.Status;
import com.getmoney.repository.UsuarioRepository;
import com.getmoney.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/autenticacao")
@Tag(name="Autenticacao", description = "Api de gerenciamento de autenticacao")
public class AutenticacaoController {


    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TokenService tokenService;

    public AutenticacaoController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    /**
     * Cria um novo usuario no sistema
     */
    @PostMapping("/registrarUsuario")
    @Operation(summary = "Registrar novo usuário", description = "Endpoint para registrar um novo usuário")
    public ResponseEntity registrarUsuario(@RequestBody @Valid RegistroRequestDTO registroRequestDTO) {
        if(this.usuarioRepository.findByEmail(registroRequestDTO.getEmail()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedSenha = new BCryptPasswordEncoder().encode(registroRequestDTO.getSenha());

        Usuario novoUsuario = modelMapper.map(registroRequestDTO, Usuario.class);
        novoUsuario.setSenha(encryptedSenha);
        novoUsuario.setDataCriacao(LocalDate.now());
        novoUsuario.setStatus(Status.ATIVO);

        this.usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }

    /**
     * Autentica um usuário com base nas credenciais fornecidas
     *
     */
    @PostMapping("/autenticarUsuario")
    @Operation(summary = "Autentica o usuário", description = "Endpoint para autenticar o usuário")
    public ResponseEntity<LoginResponseDTO> autenticarUsuario(@RequestBody @Valid LoginRequestDTO loginRequest) {
        try {
            var emailSenha = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha());
            var autenticacao = this.authenticationManager.authenticate(emailSenha);

            Usuario usuarioAutenticado = (Usuario) autenticacao.getPrincipal(); //retorna o objeto que representa o usuário autenticado
            var token = tokenService.generateToken(usuarioAutenticado); //Gera o token JWT para o usuário.
            LoginResponseDTO loginResponse = modelMapper.map(usuarioAutenticado, LoginResponseDTO.class);
            loginResponse.setToken(token);
            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

