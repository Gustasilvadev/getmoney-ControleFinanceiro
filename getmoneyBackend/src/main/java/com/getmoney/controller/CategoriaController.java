package com.getmoney.controller;

import com.getmoney.dto.request.CategoriaRequestDTO;
import com.getmoney.dto.response.CategoriaResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Usuario;
import com.getmoney.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity <List<Categoria>> listarCategorias(){
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/listarPorCategoriaId/{categoriaId}")
    @Operation(summary = "Listar categoria pelo id de categoria", description = "Endpoint para obter categoria pelo id de categoria")
    public ResponseEntity<Categoria>listarPorCategoriaId(@PathVariable Integer categoriaId){
        Categoria categoria = categoriaService.listarPorCategoriaId(categoriaId);
        if(categoria == null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(categoria);
        }
    }

    @GetMapping("/listarPorCategoriaTipo/{CategoriaTipo}")
    @Operation(summary = "Listar categoria pelo tipo de categoria", description = "Endpoint para obter categoria pelo tipo de categoria")
    public ResponseEntity<List<Categoria>> listarPorTipo(@PathVariable("CategoriaTipo") Integer categoriaTipo) {
        List<Categoria> categorias = categoriaService.listarPorCategoriaTipo(categoriaTipo);

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categorias);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova categoria", description = "Endpoint para criar um novo registro de categoria")
    public ResponseEntity<CategoriaResponseDTO> criarCategoria(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criarCategoria(categoriaRequestDTO));

    }

    @PutMapping("/editarCategoria/{categoriaId}")
    @Operation(summary="Editar categorias pelo id da categoria", description="Endpoint para editar pelo id da categoria")
    public ResponseEntity<CategoriaResponseDTO> editarCategoria(@PathVariable Integer categoriaId,
                                                 @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
        return ResponseEntity.ok(categoriaService.editarPorCategoriaId(categoriaId, categoriaRequestDTO));
    }

    @DeleteMapping("/deletarPorCategoriaId/{categoriaId}")
    @Operation(summary = "Deletar categoria", description = "Endpoint para deletar um novo registro de categoria")
    public ResponseEntity<Void> deletarPorCategoriaId(@PathVariable Integer categoriaId) {
        categoriaService.deletarPorCategoriaId(categoriaId);
        return ResponseEntity.noContent().build();
    }


}
