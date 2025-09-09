package com.getmoney.dto.response;

import com.getmoney.entity.Meta;
import com.getmoney.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MetaResponseDTO {

    private Integer id;
    private String nome;
    private BigDecimal valorAlvo;
    private Status status;
    private LocalDate data;
    private Integer usuarioId;
    private List<TransacaoBasicaResponseDTO> transacoes = new ArrayList<>();;


    public MetaResponseDTO(Meta meta) {
        this.id = meta.getId();
        this.nome = meta.getNome();
        this.valorAlvo = meta.getValorAlvo();
        this.status = meta.getStatus();
        this.data = meta.getData();
        this.usuarioId = meta.getUsuario().getId();
        this.transacoes = transacoes != null ? transacoes : new ArrayList<>();
    }

    public MetaResponseDTO() {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public List<TransacaoBasicaResponseDTO> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacaoBasicaResponseDTO> transacoes) {
        this.transacoes = transacoes;
    }
}
