package com.getmoney.controller;

import com.getmoney.dto.response.CategoriaValorTotalResponseDTO;
import com.getmoney.dto.response.ResumoFinanceiroResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.service.CategoriaService;
import com.getmoney.service.ResumoFinanceiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resumoFinanceiro")
@Tag(name="Resumo Financeiro", description = "Api de gerenciamento financeiro")
public class ResumoFinanceiroController {

    private ResumoFinanceiroService resumoFinanceiro;
    private CategoriaService categoriaService;

    public ResumoFinanceiroController(ResumoFinanceiroService resumoFinanceiro, CategoriaService categoriaService) {
        this.resumoFinanceiro = resumoFinanceiro;
        this.categoriaService = categoriaService;
    }

    /**
     * Endpoint para obter o resumo financeiro do usuário autenticado.
     * Retorna um DTO contendo informações como receitas, despesas,
     * lucro.
     */
    @GetMapping("/listarLucro")
    @Operation(summary = "Listar resumo financeiro", description = "Endpoint para obter o resumo financeiro do usuário autenticado")
    public ResponseEntity<ResumoFinanceiroResponseDTO> getResumoFinanceiro(@AuthenticationPrincipal Usuario usuario) {

        ResumoFinanceiroResponseDTO resumo = resumoFinanceiro.getResumoFinanceiro(usuario.getId());
        return ResponseEntity.ok(resumo);
    }

    @GetMapping("/listarValorTotal/categoria")
    @Operation(summary = "Listar valor total por categoria", description = "Endpoint para obter o valor total de cada categoria do usuário autenticado")
    public ResponseEntity<List<CategoriaValorTotalResponseDTO>> getCategoriasComValorTotal(@AuthenticationPrincipal Usuario usuario) {

        List<CategoriaValorTotalResponseDTO> resultado =
                categoriaService.listarCategoriasComValorTotal(usuario.getId());

        return ResponseEntity.ok(resultado);
    }
}
