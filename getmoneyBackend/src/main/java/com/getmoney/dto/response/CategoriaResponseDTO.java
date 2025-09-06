package com.getmoney.dto.response;

import com.getmoney.entity.Categoria;
import com.getmoney.enums.CategoriaTipo;

import java.util.ArrayList;
import java.util.List;

public class CategoriaResponseDTO {

    private Integer id;
    private String nome;
    private CategoriaTipo tipo;
    private Integer status;
    private List<TransacaoResponseDTO> transacoes;

    public CategoriaResponseDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.tipo = categoria.getTipo();
        this.status = categoria.getStatus();
        this.transacoes = transacoes != null ? transacoes : new ArrayList<>();
    }

    public CategoriaResponseDTO() {}


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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TransacaoResponseDTO> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacaoResponseDTO> transacoes) {
        this.transacoes = transacoes;
    }
}