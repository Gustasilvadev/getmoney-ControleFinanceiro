package com.getmoney.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EvolucaoMensalResponseDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
    private LocalDate periodo;
    private BigDecimal totalDespesas;
    private BigDecimal totalReceitas;
    private BigDecimal saldo; // receitas - despesas


    public EvolucaoMensalResponseDTO() {
    }


    public EvolucaoMensalResponseDTO(LocalDate periodo, BigDecimal totalDespesas, BigDecimal totalReceitas) {
        this.periodo = periodo;
        this.totalDespesas = totalDespesas;
        this.totalReceitas = totalReceitas;
        this.saldo = totalReceitas.subtract(totalDespesas != null ? totalDespesas : BigDecimal.ZERO);
    }


    public LocalDate getPeriodo() { return periodo; }
    public void setPeriodo(LocalDate periodo) { this.periodo = periodo; }

    public BigDecimal getTotalDespesas() { return totalDespesas; }
    public void setTotalDespesas(BigDecimal totalDespesas) {
        this.totalDespesas = totalDespesas;
        this.calcularSaldo();
    }

    public BigDecimal getTotalReceitas() { return totalReceitas; }
    public void setTotalReceitas(BigDecimal totalReceitas) {
        this.totalReceitas = totalReceitas;
        this.calcularSaldo();
    }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    // calcular saldo automaticamente
    private void calcularSaldo() {
        BigDecimal despesas = this.totalDespesas != null ? this.totalDespesas : BigDecimal.ZERO;
        BigDecimal receitas = this.totalReceitas != null ? this.totalReceitas : BigDecimal.ZERO;
        this.saldo = receitas.subtract(despesas);
    }
}