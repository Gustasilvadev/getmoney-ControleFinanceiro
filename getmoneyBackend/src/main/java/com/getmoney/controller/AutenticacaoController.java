package com.getmoney.controller;

import com.getmoney.dto.request.LoginRequestDTO;
import com.getmoney.dto.request.RegistroRequestDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.repository.UsuarioRepository;
import com.getmoney.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacao")
@Tag(name="Autenticacao", description = "Api de gerenciamento de autenticacao")
public class AutenticacaoController {


    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
     *
     */
    @PostMapping("/registrarUsuario")
    @Operation(summary = "Registrar novo usuário", description = "Endpoint para registrar um novo usuário")
    public ResponseEntity registrarUsuario(@RequestBody @Valid RegistroRequestDTO requestDTO) {
        if(this.usuarioRepository.findByEmail(requestDTO.getEmail())!=null)
            return ResponseEntity.badRequest().build();

        String encryptedSenha = new BCryptPasswordEncoder().encode(requestDTO.getSenha());
        Usuario novoUsuario = new Usuario(requestDTO.getNome(),requestDTO.getEmail(),encryptedSenha);

        this.usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }

    /**
     * Autentica um usuário com base nas credenciais fornecidas
     *
     */
    @PostMapping("/autenticarUsuario")
    @Operation(summary = "Autentica o usuário", description = "Endpoint para autenticar o usuário")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid LoginRequestDTO loginRequest) {
        var emailSenha = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha());
        var autenticacao = this.authenticationManager.authenticate(emailSenha);
        var token = tokenService.generateToken((Usuario) autenticacao.getPrincipal());
        return ResponseEntity.ok(token);
    }
}
