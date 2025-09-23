package com.getmoney.dto.response;

import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;

import java.util.List;

public class CategoriaTransacaoResponseDTO {
    private Integer id;
    private String nome;
    private CategoriaTipo tipo;
    private Status status;

    private Integer usuarioId;
    private List<TransacaoPorCategoriaResponseDTO> transacoes;

    public CategoriaTransacaoResponseDTO() {
    }
    public CategoriaTransacaoResponseDTO(Integer id, String nome, CategoriaTipo tipo, Status status,Integer usuarioId, List<TransacaoPorCategoriaResponseDTO> transacoes) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.status = status;
        this.usuarioId =usuarioId;
        this.transacoes = transacoes;
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

    public CategoriaTipo getTipo() {
        return tipo;
    }

    public void setTipo(CategoriaTipo tipo) {
        this.tipo = tipo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<TransacaoPorCategoriaResponseDTO> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacaoPorCategoriaResponseDTO> transacoes) {
        this.transacoes = transacoes;
    }
}
