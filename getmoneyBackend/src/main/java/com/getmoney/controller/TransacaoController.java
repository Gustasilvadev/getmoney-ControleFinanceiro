package com.getmoney.controller;

import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.dto.request.TransacaoUpdateRequestDTO;
import com.getmoney.dto.response.TransacaoResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Transacao;
import com.getmoney.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacao")
@Tag(name="Transacao", description = "Api de gerenciamento de transacoes")
public class TransacaoController {

    private TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }


    @GetMapping("/listar")
    @Operation(summary="Listar transacoes", description="Endpoint para listar todas as transacoes")
    public ResponseEntity <List<TransacaoResponseDTO>> listarTransacoes(){
        return ResponseEntity.ok(transacaoService.listarTransacoes());
    }

    @GetMapping("/listarPorTransacaoId/{transacaoId}")
    @Operation(summary = "Listar transacao pelo id de transacao", description = "Endpoint para obter transacao pelo id de transacao")
    public ResponseEntity<TransacaoResponseDTO>listarPorTransacaoId(@PathVariable Integer transacaoId){
        try {
            TransacaoResponseDTO transacao = transacaoService.listarPorTransacaoId(transacaoId);
            return ResponseEntity.ok(transacao);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova transacao", description = "Endpoint para criar um novo registro de transacao")
    public ResponseEntity<TransacaoResponseDTO> criarTransacao(@Valid @RequestBody TransacaoRequestDTO transacaoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.criarTransacao(transacaoRequestDTO));
    }

    @PutMapping("/editarPorTransacaoId/{transacaoId}")
    @Operation(summary="Editar transacoes pelo id da transacao", description="Endpoint para editar pelo id da transacao")
    public ResponseEntity<TransacaoResponseDTO> editarPorTransacaoId(@PathVariable Integer transacaoId,
                                                                     @Valid @RequestBody TransacaoUpdateRequestDTO transacaoUpdateRequestDTO) {

        return ResponseEntity.ok(transacaoService.editarPorTransacaoId(transacaoId,transacaoUpdateRequestDTO));
    }

    @DeleteMapping("/deletarPorTransacaoId/{transacaoId}")
    @Operation(summary = "Deletar transacao", description = "Endpoint para deletar um novo registro de transacao")
    public ResponseEntity<Void> deletarPorTransacaoId(@PathVariable Integer transacaoId) {
        transacaoService.deletarPorTransacaoId(transacaoId);
        return ResponseEntity.noContent().build();
    }

}
