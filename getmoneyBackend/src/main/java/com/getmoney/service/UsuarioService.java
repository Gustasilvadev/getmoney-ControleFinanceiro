package com.getmoney.service;

import com.getmoney.dto.request.AlterarSenhaRequestDTO;
import com.getmoney.dto.request.UsuarioRequestDTO;
import com.getmoney.dto.response.UsuarioResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.repository.UsuarioRepository;
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

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public List<UsuarioResponseDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.listarUsuariosAtivos();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO listarPorUsuarioId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.ObterUsuarioPeloId(usuarioId);


        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }


    @Transactional
    public UsuarioResponseDTO editarPorUsuarioId(Integer usuarioId, UsuarioRequestDTO usuarioRequestDTO) {
        Usuario usuarioExistente = usuarioRepository.ObterUsuarioPeloId(usuarioId);
        if (usuarioExistente == null) {
            throw new RuntimeException("Usuario não encontrada com ID: " + usuarioId);
        }

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper.map(usuarioRequestDTO, usuarioExistente);

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
