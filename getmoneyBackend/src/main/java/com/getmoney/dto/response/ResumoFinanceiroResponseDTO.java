package com.getmoney.dto.response;

import java.math.BigDecimal;

public class ResumoFinanceiroResponseDTO {

    private BigDecimal receitas;
    private BigDecimal despesas;
    private BigDecimal lucro;

    public ResumoFinanceiroResponseDTO() {
    }

    public ResumoFinanceiroResponseDTO(BigDecimal receitas, BigDecimal despesas, BigDecimal lucro) {
        this.receitas = receitas;
        this.despesas = despesas;
        this.lucro = lucro;
    }

    public BigDecimal getReceitas() {
        return receitas;
    }

    public void setReceitas(BigDecimal receitas) {
        this.receitas = receitas;
    }

    public BigDecimal getDespesas() {
        return despesas;
    }

    public void setDespesas(BigDecimal despesas) {
        this.despesas = despesas;
    }

    public BigDecimal getLucro() {
        return lucro;
    }

    public void setLucro(BigDecimal lucro) {
        this.lucro = lucro;
    }
}
