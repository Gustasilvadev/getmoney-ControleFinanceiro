package com.getmoney.dto.response;

import com.getmoney.enums.Status;

import java.math.BigDecimal;

public class ProgressoMetaResponseDTO {

    private Integer metaId;
    private String metaNome;
    private BigDecimal valorAlvo;
    private BigDecimal valorAtual;
    private BigDecimal percentualConcluido;
    private Status status;

    public ProgressoMetaResponseDTO() {
    }

    public ProgressoMetaResponseDTO(Integer metaId, String metaNome, BigDecimal valorAlvo, BigDecimal valorAtual, BigDecimal percentualConcluido, Status status) {
        this.metaId = metaId;
        this.metaNome = metaNome;
        this.valorAlvo = valorAlvo;
        this.valorAtual = valorAtual;
        this.percentualConcluido = percentualConcluido;
        this.status = status;
    }

    public Integer getMetaId() {
        return metaId;
    }

    public void setMetaId(Integer metaId) {
        this.metaId = metaId;
    }

    public String getMetaNome() {
        return metaNome;
    }

    public void setMetaNome(String metaNome) {
        this.metaNome = metaNome;
    }

    public BigDecimal getValorAlvo() {
        return valorAlvo;
    }

    public void setValorAlvo(BigDecimal valorAlvo) {
        this.valorAlvo = valorAlvo;
    }

    public BigDecimal getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    public BigDecimal getPercentualConcluido() {
        return percentualConcluido;
    }

    public void setPercentualConcluido(BigDecimal percentualConcluido) {
        this.percentualConcluido = percentualConcluido;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
