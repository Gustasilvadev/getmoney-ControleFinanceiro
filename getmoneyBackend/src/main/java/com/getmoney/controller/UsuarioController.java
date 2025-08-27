package com.getmoney.controller;


import com.getmoney.entity.Usuario;
import com.getmoney.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }


    @PutMapping("/editarPorUsuarioId/{usuarioId}")
    @Operation(summary="Editar usuário pelo id do usuário", description="Endpoint para editar pelo id do usuário")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Integer usuarioId,
                                                 @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.editarPorUsuarioId(usuarioId, usuario);
            return ResponseEntity.ok(usuarioAtualizado); // 200 OK com a usuario atualizado
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 se não achar a usuario
        }
    }

}

