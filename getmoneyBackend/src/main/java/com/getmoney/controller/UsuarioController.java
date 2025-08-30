package com.getmoney.controller;


import com.getmoney.dto.request.UsuarioRequestDTO;
import com.getmoney.dto.response.UsuarioResponseDTO;
import com.getmoney.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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
    public ResponseEntity<UsuarioResponseDTO> editarUsuario(@PathVariable Integer usuarioId,
                                                            @RequestBody @Valid UsuarioRequestDTO usuarioRequestDTO) {
        try {
            UsuarioResponseDTO usuarioAtualizado = usuarioService.editarPorUsuarioId(usuarioId, usuarioRequestDTO);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
