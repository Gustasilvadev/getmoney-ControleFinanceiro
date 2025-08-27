package com.getmoney.controller;

import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Transacao;
import com.getmoney.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity <List<Transacao>> listarTransacoes(){
        return ResponseEntity.ok(transacaoService.listarTransacoes());
    }

    @GetMapping("/listarPorTransacaoId/{transacaoId}")
    @Operation(summary = "Listar transacao pelo id de transacao", description = "Endpoint para obter transacao pelo id de transacao")
    public ResponseEntity<Transacao>listarPorTransacaoId(@PathVariable Integer transacaoId){
        Transacao transacao = transacaoService.listarPorTransaccaoId(transacaoId);
        if(transacao == null) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(transacao);
        }
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova transacao", description = "Endpoint para criar um novo registro de transacao")
    public ResponseEntity<Transacao> criarTransacao(@RequestBody TransacaoRequestDTO requestDTO) {
        // Service converte DTO --> Entidade e salva no banco
        Transacao transacaoSalva = transacaoService.criarTransacao(requestDTO);
        // Devolve a entidade salva
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoSalva);
    }

    @PutMapping("/editarPorTransacaoId/{transacaoId}")
    @Operation(summary="Editar transacoes pelo id da transacao", description="Endpoint para editar pelo id da transacao")
    public ResponseEntity<Transacao> editarPorTransacaoId(@PathVariable Integer transacaoId,
                                                          @RequestBody Transacao transacao) {
        try {
            Transacao transacaoAtualizada = transacaoService.editarPorTransacaoId(transacaoId, transacao);
            return ResponseEntity.ok(transacaoAtualizada); // 200 OK com a transacao atualizado
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 se não achar a transacao
        }
    }

    @DeleteMapping("/deletarPorTransacaoId/{transacaoId}")
    @Operation(summary = "Deletar transacao", description = "Endpoint para deletar um novo registro de transacao")
    public ResponseEntity<Void> deletarPorTransacaoId(@PathVariable Integer transacaoId) {
        transacaoService.deletarPorTransacaoId(transacaoId);
        return ResponseEntity.noContent().build();
    }

}
