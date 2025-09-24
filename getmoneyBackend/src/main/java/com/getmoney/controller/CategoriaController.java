package com.getmoney.controller;

import com.getmoney.dto.request.CategoriaRequestDTO;
import com.getmoney.dto.response.CategoriaBasicaResponseDTO;
import com.getmoney.dto.response.CategoriaResponseDTO;
import com.getmoney.dto.response.CategoriaTransacaoResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@Tag(name="Categoria", description = "Api de gerenciamento de categorias")
public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @GetMapping("/listar")
    @Operation(summary="Listar categorias", description="Endpoint para listar todas as categorias")
    public ResponseEntity <List<CategoriaResponseDTO>> listarCategorias(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(categoriaService.listarCategorias(usuario.getId()));
    }


    @GetMapping("/listarPorCategoriaId/{categoriaId}")
    @Operation(summary = "Listar categoria pelo id de categoria", description = "Endpoint para obter categoria pelo id de categoria")
    public ResponseEntity<CategoriaResponseDTO>listarPorCategoriaId(@PathVariable Integer categoriaId,
                                                                    @AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(categoriaService.listarPorCategoriaId(categoriaId,usuario.getId()));
    }

    @GetMapping("/listarPorCategoriaTipo/{categoriaTipo}")
    @Operation(summary = "Listar categoria pelo tipo de categoria", description = "Endpoint para obter categoria pelo tipo de categoria")
    public ResponseEntity<List<CategoriaResponseDTO >> listarPorCategoriaTipo(
            @PathVariable("categoriaTipo") Integer categoriaTipo,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(categoriaService.listarPorCategoriaTipo(categoriaTipo, usuario.getId()));
    }

    @GetMapping("/listarPorCategoriaNome")
    @Operation(summary = "Listar categorias por nome", description = "Endpoint para listar categorias filtrando por nome. Se nenhum nome for informado, retorna todas as categorias ativas.")
    public ResponseEntity<List<CategoriaBasicaResponseDTO>> listarPorCategoriaNome(@RequestParam(required = false) String nome,
                                                                                   @AuthenticationPrincipal Usuario usuario) {
        List<CategoriaBasicaResponseDTO> categorias = categoriaService.buscarPorCategoriaNome(nome, usuario.getId());
        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova categoria", description = "Endpoint para criar um novo registro de categoria")
    public ResponseEntity<CategoriaResponseDTO> criarCategoria(@RequestBody @Valid  CategoriaRequestDTO categoriaRequestDTO,
                                                               @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criarCategoria(categoriaRequestDTO, usuario.getId()));

    }

    @PutMapping("/editarCategoria/{categoriaId}")
    @Operation(summary="Editar categorias pelo id da categoria", description="Endpoint para editar pelo id da categoria")
    public ResponseEntity<CategoriaResponseDTO> editarCategoria(@PathVariable Integer categoriaId,
                                                                @RequestBody CategoriaRequestDTO categoriaRequestDTO,
                                                                @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(categoriaService.editarPorCategoriaId(categoriaId, categoriaRequestDTO, usuario.getId()));
    }

    @DeleteMapping("/deletarPorCategoriaId/{categoriaId}")
    @Operation(summary = "Deletar categoria", description = "Endpoint para deletar um novo registro de categoria")
    public ResponseEntity<Void> deletarPorCategoriaId(@PathVariable Integer categoriaId,
                                                      @AuthenticationPrincipal Usuario usuario) {
        categoriaService.deletarPorCategoriaId(categoriaId, usuario.getId());
        return ResponseEntity.noContent().build();
    }


}
