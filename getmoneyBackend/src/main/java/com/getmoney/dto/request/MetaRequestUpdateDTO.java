package com.getmoney.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetaRequestUpdateDTO {

    private String nome;
    private BigDecimal valorAlvo;
    private Integer status;
    private LocalDate data;


    public MetaRequestUpdateDTO() {
    }

    public MetaRequestUpdateDTO(String nome, BigDecimal valorAlvo, Integer status, LocalDate data) {
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.status = status;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorAlvo() {
        return valorAlvo;
    }

    public void setValorAlvo(BigDecimal valorAlvo) {
        this.valorAlvo = valorAlvo;
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
