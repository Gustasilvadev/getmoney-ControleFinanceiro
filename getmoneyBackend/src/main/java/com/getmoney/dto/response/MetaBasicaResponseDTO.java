package com.getmoney.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetaBasicaResponseDTO {
    private Integer id;
    private String nome;
    private BigDecimal valorAlvo;
    private LocalDate data;

    public MetaBasicaResponseDTO() {
    }

    public MetaBasicaResponseDTO(Integer id, String nome, BigDecimal valorAlvo, LocalDate data) {
        this.id = id;
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.data = data;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}

