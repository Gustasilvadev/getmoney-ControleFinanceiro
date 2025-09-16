package com.getmoney.controller;

import com.getmoney.dto.request.TransacaoRequestDTO;
import com.getmoney.dto.request.TransacaoUpdateRequestDTO;
import com.getmoney.dto.response.CategoriaBasicaResponseDTO;
import com.getmoney.dto.response.CategoriaResponseDTO;
import com.getmoney.dto.response.TransacaoBasicaResponseDTO;
import com.getmoney.dto.response.TransacaoResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.service.CategoriaService;
import com.getmoney.service.TransacaoService;
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
@RequestMapping("/api/transacao")
@Tag(name="Transacao", description = "Api de gerenciamento de transacoes")
public class TransacaoController {

    private TransacaoService transacaoService;
    private CategoriaService categoriaService;

    public TransacaoController(TransacaoService transacaoService, CategoriaService categoriaService) {
        this.transacaoService = transacaoService;
        this.categoriaService = categoriaService;
    }


    @GetMapping("/listar")
    @Operation(summary="Listar transacoes", description="Endpoint para listar todas as transacoes")
    public ResponseEntity <List<TransacaoResponseDTO>> listarTransacoes( @AuthenticationPrincipal Usuario usuario){
        return ResponseEntity.ok(transacaoService.listarTransacoesAtivas());
    }

    // Endpoint: /api/transacao/{id}/categoria
    @GetMapping("/{transacaoId}/categoria")
    @Operation(summary = "Obter categoria de uma transação",description = "Endpoint para recuperar a categoria associada a uma transação específica")
    public ResponseEntity<CategoriaBasicaResponseDTO> getCategoriaDaTransacao(@PathVariable Integer transacaoId) {
        TransacaoResponseDTO transacao = transacaoService.obterTransacaoAtivaPorId(transacaoId);

        if (transacao == null || transacao.getCategoriaId() == null) {
            return ResponseEntity.notFound().build();
        }

        CategoriaBasicaResponseDTO categoria = categoriaService.listarTransacaoCategoriaId(transacao.getCategoriaId());

        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(categoria);
    }


    // Endpoint: /api/transacao/{id}/categoria/{categoriaId}
    @GetMapping("/{transacaoId}/categoria/{categoriaId}")
    @Operation(summary = "Buscar transação por ID e categoria por ID", description = "Retorna os dados de uma transação específica se ela pertencer à categoria informada")
    public ResponseEntity<TransacaoBasicaResponseDTO> getTransacaoPorCategoria(
            @PathVariable Integer transacaoId,
            @PathVariable Integer categoriaId,
            @AuthenticationPrincipal Usuario usuario) {

        TransacaoBasicaResponseDTO transacao = transacaoService.obterTransacaoPorCategoria(transacaoId, categoriaId, usuario.getId());
        if (transacao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transacao);
    }

    // Endpoint: /api/meta/{id}/transacao
    @GetMapping("/meta/{metaId}/transacao")
    @Operation(summary = "Listar transações da meta", description = "Retorna todas as transações associadas a uma meta")
    public ResponseEntity<List<TransacaoBasicaResponseDTO>> getTransacoesPorMeta(@PathVariable Integer metaId) {
        List<TransacaoBasicaResponseDTO> transacoes = transacaoService.listarTransacoesPorMeta(metaId);

        if (transacoes == null || transacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transacoes);
    }

    // Endpoint: /api/meta/{id}/transacao/{transacaoId}
    @GetMapping("/meta/{metaId}/transacao/{transacaoId}")
    @Operation(summary = "Obter transação filtrada por meta", description = "Retorna os dados de uma transação específica se ela pertencer à meta informada")
    public ResponseEntity<TransacaoResponseDTO> getTransacaoPorMeta(@PathVariable Integer metaId, @PathVariable Integer transacaoId) {
        TransacaoResponseDTO transacao = transacaoService.obterTransacaoPorMeta(transacaoId, metaId);

        if (transacao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transacao);
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
    public ResponseEntity<TransacaoResponseDTO> criarTransacao(@RequestBody @Valid TransacaoRequestDTO transacaoRequestDTO) {
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
