package com.getmoney.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransacaoUpdateRequestDTO {
    private BigDecimal valor;
    private String descricao;
    private LocalDate data;
    private Integer status;
    private List<Integer> metasIds;

    public TransacaoUpdateRequestDTO() {}

    public TransacaoUpdateRequestDTO(BigDecimal valor, String descricao, LocalDate data, Integer status) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.status = status;
        this.metasIds = new ArrayList<>();
    }

    // Getters e Setters (SEM categoriaId)
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Integer> getMetasIds() {
        return metasIds;
    }

    public void setMetasIds(List<Integer> metasIds) {
        this.metasIds = metasIds;
    }
}