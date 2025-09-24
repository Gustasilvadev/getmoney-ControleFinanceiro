package com.getmoney.controller;


import com.getmoney.dto.request.AlterarSenhaRequestDTO;
import com.getmoney.dto.request.UsuarioRequestDTO;
import com.getmoney.dto.response.UsuarioResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/usuario")
@Tag(name="Usuario", description = "Api de gerenciamento de usuarios")

public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }



    @GetMapping("/listar")
    @Operation(summary="Listar usuários", description="Endpoint para listar todos os usuários")
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }


    @PutMapping("/editarPorUsuarioId/{usuarioId}")
    @Operation(summary="Editar usuário pelo id do usuário", description="Endpoint para editar pelo id do usuário")
    public ResponseEntity<UsuarioResponseDTO> editarUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO,
                                                            @AuthenticationPrincipal Usuario usuario) {
        try {
            UsuarioResponseDTO usuarioAtualizado = usuarioService.editarPorUsuarioId(usuario.getId(), usuarioRequestDTO);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Busca um usuário pelo ID
     * Lança uma exceção se o usuário não for encontrado.
     */
    @GetMapping("/listarPorUsuarioId/{usuarioId}")
    @Operation(summary = "Buscar usuário pelo ID", description = "Endpoint para obter um usuário pelo seu Id")
    public ResponseEntity<UsuarioResponseDTO> listarPorUsuarioId(@PathVariable Integer usuarioId) {
        try {
            UsuarioResponseDTO usuario = usuarioService.listarPorUsuarioId(usuarioId);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/alterarSenha")
    @Operation(summary = "Alterar senha do usuario", description = "Endpoint para alterar a senha atual por uma senha nova")
    public ResponseEntity<Void> alterarSenha(
            @RequestHeader("Authorization") String token,
            @RequestBody @Valid AlterarSenhaRequestDTO alterarSenhaRequestDTO) {

        usuarioService.alterarSenha(token, alterarSenhaRequestDTO);
        return ResponseEntity.ok().build();
    }
}
