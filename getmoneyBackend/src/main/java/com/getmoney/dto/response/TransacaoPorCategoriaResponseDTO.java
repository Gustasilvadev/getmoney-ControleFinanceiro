package com.getmoney.dto.response;

import com.getmoney.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoPorCategoriaResponseDTO {

    private Integer id;
    private BigDecimal valor;
    private String descricao;
    private LocalDate data;
    private Status status;
    private Integer usuarioId;

    public TransacaoPorCategoriaResponseDTO() {
    }

    public TransacaoPorCategoriaResponseDTO(Integer id, BigDecimal valor, String descricao, LocalDate data, Status status, Integer usuarioId) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.status = status;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
}

