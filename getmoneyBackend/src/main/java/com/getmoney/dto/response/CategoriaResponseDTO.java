package com.getmoney.dto.response;

import com.getmoney.entity.Categoria;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;

import java.util.ArrayList;
import java.util.List;

public class CategoriaResponseDTO {

    private Integer id;
    private String nome;
    private CategoriaTipo tipo;
    private Status status;

    private Integer usuarioId;

    private List<TransacaoPorCategoriaResponseDTO> transacoes;

    public CategoriaResponseDTO(Integer id, String nome, CategoriaTipo tipo, Status status, Integer usuarioid, List<TransacaoPorCategoriaResponseDTO> transacoes) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.status = status;
        this.usuarioId=usuarioid;
        this.transacoes = transacoes;
    }
    public CategoriaResponseDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.tipo = categoria.getTipo();
        this.status = categoria.getStatus();
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