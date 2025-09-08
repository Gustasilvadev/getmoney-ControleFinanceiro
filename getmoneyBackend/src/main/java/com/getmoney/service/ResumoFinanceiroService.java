package com.getmoney.service;

import com.getmoney.dto.response.ResumoFinanceiroResponseDTO;
import com.getmoney.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ResumoFinanceiroService {

    private final TransacaoRepository transacaoRepository;

    public ResumoFinanceiroService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public ResumoFinanceiroResponseDTO getResumoFinanceiro(Integer usuarioId) {
        BigDecimal receitas = transacaoRepository.TotalReceitas(usuarioId);
        BigDecimal despesas = transacaoRepository.TotalDespesas(usuarioId);
        BigDecimal lucro = receitas.subtract(despesas);

        return new ResumoFinanceiroResponseDTO(receitas, despesas, lucro);
    }
}
