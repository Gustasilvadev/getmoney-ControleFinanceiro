package com.getmoney.controller;

import com.getmoney.dto.response.CategoriaValorTotalResponseDTO;
import com.getmoney.dto.response.EvolucaoMensalResponseDTO;
import com.getmoney.dto.response.ProgressoMetaResponseDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estatistica")
@Tag(name="Estatisticas", description = "Api de gerenciamento de estatisticas")
public class ResumoFinanceiroController {

    private ResumoFinanceiroService resumoFinanceiroService;
    private CategoriaService categoriaService;

    public ResumoFinanceiroController(ResumoFinanceiroService resumoFinanceiroService, CategoriaService categoriaService) {
        this.resumoFinanceiroService = resumoFinanceiroService;
        this.categoriaService = categoriaService;
    }

    /**
     * Endpoint para obter o resumo financeiro do usuário autenticado.
     * Retorna informações como receitas, despesas, lucro.
     */
    @GetMapping("/listarLucro")
    @Operation(summary = "Listar resumo financeiro", description = "Endpoint para obter o resumo financeiro do usuário autenticado")
    public ResponseEntity<ResumoFinanceiroResponseDTO> getResumoFinanceiro(@AuthenticationPrincipal Usuario usuario) {

        ResumoFinanceiroResponseDTO resumo = resumoFinanceiroService.getResumoFinanceiro(usuario.getId());
        return ResponseEntity.ok(resumo);
    }

    /**
     * Endpoint para obter o valor total de cada cateria do usuario autenticado.
     */
    @GetMapping("/listarValorTotal/categoria")
    @Operation(summary = "Listar valor total por categoria", description = "Endpoint para obter o valor total de cada categoria do usuário autenticado")
    public ResponseEntity<List<CategoriaValorTotalResponseDTO>> getCategoriasComValorTotal(@AuthenticationPrincipal Usuario usuario) {

        List<CategoriaValorTotalResponseDTO> resultado =
                resumoFinanceiroService.listarCategoriasComValorTotal(usuario.getId());

        return ResponseEntity.ok(resultado);
    }


    /**
     * Retorna dados para gráfico de pizza com gastos por categoria
     */
    @GetMapping("/analisePorCategoria")
    @Operation(summary = "Listar gastos por categoria", description = "Endpoint para obter os gastos de cada categoria do usuário autenticado")
    public ResponseEntity<List<CategoriaValorTotalResponseDTO>> getResumoCategorias(
            @AuthenticationPrincipal Usuario usuario) {

        try {
            List<CategoriaValorTotalResponseDTO> resumo = resumoFinanceiroService.getResumoCategorias(usuario.getId());
            return ResponseEntity.ok(resumo);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retorna dados para gráfico de linha com evolução de receitas vs despesas
     */
    @GetMapping("/analiseEvolucaoMensal")
    @Operation(summary = "Listar receitas e despesas por periodo", description = "Endpoint para obter os despesas e receitas pelo periodo")

    public ResponseEntity<List<EvolucaoMensalResponseDTO>> getEvolucaoMensal(
            @AuthenticationPrincipal Usuario usuario,
            @RequestParam(value = "meses", required = false) Integer meses) {

        try {
            List<EvolucaoMensalResponseDTO> evolucao = resumoFinanceiroService.getEvolucaoMensal(usuario.getId(), meses);
            return ResponseEntity.ok(evolucao);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Retorna progresso de todas as metas do usuário
     */
    @GetMapping("/progressoDaMeta")
    @Operation(summary = "Listar todos progresso de metas", description = "Endpoint para obter os progressos das metas do usuário autenticado")
    public ResponseEntity<List<ProgressoMetaResponseDTO>> getProgressoMeta(
            @AuthenticationPrincipal Usuario usuario) {

        try {
            List<ProgressoMetaResponseDTO> progresso = resumoFinanceiroService.getProgressoMetas(usuario.getId());
            return ResponseEntity.ok(progresso);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
