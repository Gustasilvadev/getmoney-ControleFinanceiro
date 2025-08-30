package com.getmoney.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetaRequestDTO {

    private String nome;
    private BigDecimal valor_alvo;
    private Integer status;
    private LocalDate data;
    private Integer usuarioId;

    public MetaRequestDTO() {
    }

    public MetaRequestDTO(String nome, BigDecimal valor_alvo, Integer status, LocalDate data, Integer usuarioId) {
        this.nome = nome;
        this.valor_alvo = valor_alvo;
        this.status = status;
        this.data = data;
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor_alvo() {
        return valor_alvo;
    }

    public void setValor_alvo(BigDecimal valor_alvo) {
        this.valor_alvo = valor_alvo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
