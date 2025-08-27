package com.getmoney.service;

import com.getmoney.entity.Usuario;
import com.getmoney.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository ) {
        this.usuarioRepository = usuarioRepository;
    }


    public List<Usuario> listarUsuarios() {
        return this.usuarioRepository.findAll();
    }


    public Usuario editarPorUsuarioId(Integer id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    // Atualiza apenas se os campos forem diferentes
                    if (!usuarioExistente.getNome().equals(usuarioAtualizado.getNome())) {
                        usuarioExistente.setNome(usuarioAtualizado.getNome());
                    }

                    if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail())) {
                        usuarioExistente.setEmail(usuarioAtualizado.getEmail());
                    }
                    if (!usuarioExistente.getSenha().equals(usuarioAtualizado.getSenha())) {
                        usuarioExistente.setSenha(usuarioAtualizado.getSenha());
                    }

                    return usuarioRepository.save(usuarioExistente);
                })
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id " + id));
    }


}
