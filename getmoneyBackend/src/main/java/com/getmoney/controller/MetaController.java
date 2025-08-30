package com.getmoney.controller;

import com.getmoney.dto.request.MetaRequestDTO;
import com.getmoney.dto.request.MetaRequestUpdateDTO;
import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.dto.request.TransacaoUpdateRequestDTO;
import com.getmoney.dto.response.MetaResponseDTO;
import com.getmoney.dto.response.TransacaoResponseDTO;
import com.getmoney.service.MetaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MetaResponseDTO>> listarMetas(){
        return ResponseEntity.ok(metaService.listarMetas());
    }

    @GetMapping("/listarPorMetaId/{metaId}")
    @Operation(summary = "Listar transacao pelo id de meta", description = "Endpoint para obter meta pelo id de meta")
    public ResponseEntity<MetaResponseDTO>listarPorMetaId(@PathVariable Integer metaId){
        try {
            MetaResponseDTO meta = metaService.listarPorMetaId(metaId);
            return ResponseEntity.ok(meta);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova meta", description = "Endpoint para criar um novo registro de meta")
    public ResponseEntity<MetaResponseDTO> criarMeta(@Valid @RequestBody MetaRequestDTO metaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(metaService.criarMeta(metaRequestDTO));
    }

    @PutMapping("/editarPorMetaId/{metaId}")
    @Operation(summary="Editar metas pelo id da metas", description="Endpoint para editar pelo id da meta")
    public ResponseEntity<MetaResponseDTO> editarPorMetaId(@PathVariable Integer metaId,
                                                           @Valid @RequestBody MetaRequestUpdateDTO metaRequestUpdateDTO) {

        return ResponseEntity.ok(metaService.editarPorMetaId(metaId,metaRequestUpdateDTO));
    }

    @DeleteMapping("/deletarPorMetaId/{metaId}")
    @Operation(summary = "Deletar meta", description = "Endpoint para deletar um novo registro de meta")
    public ResponseEntity<Void> deletarPorMetaId(@PathVariable Integer metaId) {
        metaService.deletarPorMetaId(metaId);
        return ResponseEntity.noContent().build();
    }
}
