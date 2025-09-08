package com.getmoney.controller;

import com.getmoney.dto.response.ResumoFinanceiroResponseDTO;
import com.getmoney.entity.Usuario;
import com.getmoney.service.ResumoFinanceiroService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resumoFinanceiro")
public class ResumoFinanceiroController {

    private ResumoFinanceiroService resumoFinanceiro;

    public ResumoFinanceiroController(ResumoFinanceiroService resumoFinanceiro) {
        this.resumoFinanceiro = resumoFinanceiro;
    }

    /**
     * Endpoint para obter o resumo financeiro do usuário autenticado.
     * Retorna um DTO contendo informações como receitas, despesas,
     * lucro.
     */
    @GetMapping("/listarLucro")
    public ResponseEntity<ResumoFinanceiroResponseDTO> getResumoFinanceiro(@AuthenticationPrincipal Usuario usuario) {

        ResumoFinanceiroResponseDTO resumo = resumoFinanceiro.getResumoFinanceiro(usuario.getId());
        return ResponseEntity.ok(resumo);
    }
}
