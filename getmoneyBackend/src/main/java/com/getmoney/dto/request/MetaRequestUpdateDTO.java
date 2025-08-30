package com.getmoney.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetaRequestUpdateDTO {

    private String nome;
    private BigDecimal valor_alvo;
    private Integer status;
    private LocalDate data;


    public MetaRequestUpdateDTO() {
    }

    public MetaRequestUpdateDTO(String nome, BigDecimal valor_alvo, Integer status, LocalDate data) {
        this.nome = nome;
        this.valor_alvo = valor_alvo;
        this.status = status;
        this.data = data;
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
}
