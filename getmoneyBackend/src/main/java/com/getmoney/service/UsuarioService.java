package com.getmoney.service;

import com.getmoney.dto.request.UsuarioRequestDTO;
import com.getmoney.dto.response.UsuarioResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

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
        if (usuario == null) {
            throw new RuntimeException("Usuario não encontrada com ID: " + usuarioId);
        }

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


}
