package com.getmoney.service;

import com.getmoney.dto.request.AlterarSenhaRequestDTO;
import com.getmoney.dto.request.UsuarioRequestDTO;
import com.getmoney.dto.response.UsuarioResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, TokenService tokenService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Busca o usuario logado
     * Lança uma exceção se o usuário não for encontrado.
     */
    public UsuarioResponseDTO listarUsuarioLogado(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    @Transactional
    public UsuarioResponseDTO editarPorUsuarioId(Integer usuarioId, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (usuarioRequestDTO.getNome() != null) {
            usuarioExistente.setNome(usuarioRequestDTO.getNome());
        }
        if (usuarioRequestDTO.getEmail() != null) {
            usuarioExistente.setEmail(usuarioRequestDTO.getEmail());
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return modelMapper.map(usuarioAtualizado, UsuarioResponseDTO.class);
    }

    /**
     * Extrai o email do token JWT
     * Valida se a senha atual está correta
     * Verifica se a nova senha é diferente da atual
     * Criptografa e salva a nova senha
     */
    @Transactional
    public void alterarSenha(String token, AlterarSenhaRequestDTO alterarSenhaRequestDTO) {
        String email = tokenService.extrairEmailToken(token.replace("Bearer ", ""));


        Usuario usuario = usuarioRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com esse email: " + email));


        if (!passwordEncoder.matches(alterarSenhaRequestDTO.getSenhaAtual(), usuario.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        if (passwordEncoder.matches(alterarSenhaRequestDTO.getNovaSenha(), usuario.getSenha())) {
            throw new RuntimeException("Nova senha não pode ser igual à atual");
        }

        usuario.setSenha(passwordEncoder.encode(alterarSenhaRequestDTO.getNovaSenha()));
        usuarioRepository.save(usuario);
    }


}
