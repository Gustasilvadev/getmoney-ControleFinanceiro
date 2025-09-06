package com.getmoney.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransacaoUpdateRequestDTO {
    private BigDecimal valor;
    private String descricao;
    private LocalDate data;
    private Integer status;

    private Integer usuarioId;
    private Integer categoriaId;
    private List<Integer> metasId;

    public TransacaoUpdateRequestDTO() {}

    public TransacaoUpdateRequestDTO(BigDecimal valor, String descricao, LocalDate data, Integer status, Integer usuarioId, Integer categoriaId, List<Integer> metasId) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.status = status;
        this.usuarioId = usuarioId;
        this.categoriaId = categoriaId;
        this.metasId = metasId;
    }

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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public List<Integer> getMetasId() {
        return metasId;
    }

    public void setMetasId(List<Integer> metasId) {
        this.metasId = metasId;
    }
}