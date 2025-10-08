package com.getmoney.controller;

import com.getmoney.dto.request.MetaRequestDTO;
import com.getmoney.dto.request.MetaRequestUpdateDTO;
import com.getmoney.dto.response.MetaResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.service.MetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meta")
@Tag(name="Meta", description = "Api de gerenciamento de metas")
public class MetaController {

    private MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @GetMapping("/listar")
    @Operation(summary="Listar metas", description="Endpoint para listar todas as metas")
    public ResponseEntity<List<MetaResponseDTO>> listarMetas(@AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(metaService.listarMetas(usuario.getId()));
    }

    @GetMapping("/listarPorMetaId/{metaId}")
    @Operation(summary = "Listar transacao pelo id de meta", description = "Endpoint para obter meta pelo id de meta")
    public ResponseEntity<MetaResponseDTO>listarPorMetaId(@PathVariable Integer metaId,
                                                          @AuthenticationPrincipal Usuario usuario){
        try {
            MetaResponseDTO meta = metaService.listarPorMetaId(metaId, usuario.getId());
            return ResponseEntity.ok(meta);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova meta", description = "Endpoint para criar um novo registro de meta")
    public ResponseEntity<MetaResponseDTO> criarMeta(@RequestBody @Valid  MetaRequestDTO metaRequestDTO,
                                                     @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(metaService.criarMeta(metaRequestDTO, usuario.getId()));
    }

    @PutMapping("/editarPorMetaId/{metaId}")
    @Operation(summary="Editar metas pelo id da metas", description="Endpoint para editar pelo id da meta")
    public ResponseEntity<MetaResponseDTO> editarPorMetaId(@PathVariable Integer metaId,
                                                           @RequestBody @Valid  MetaRequestUpdateDTO metaRequestUpdateDTO,
                                                           @AuthenticationPrincipal Usuario usuario) {

        return ResponseEntity.ok(metaService.editarPorMetaId(metaId,metaRequestUpdateDTO, usuario.getId()));
    }

    @DeleteMapping("/deletarPorMetaId/{metaId}")
    @Operation(summary = "Deletar meta", description = "Endpoint para deletar um novo registro de meta")
    public ResponseEntity<Void> deletarPorMetaId(@PathVariable Integer metaId,
                                                 @AuthenticationPrincipal Usuario usuario) {
        metaService.deletarPorMetaId(metaId,usuario.getId());
        return ResponseEntity.noContent().build();
    }
}
