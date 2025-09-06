package com.getmoney.dto.response;

import com.getmoney.entity.Usuario;

import java.time.LocalDate;
import java.util.List;

public class UsuarioResponseDTO {

    private Integer id;
    private String nome;
    private String email;
    private LocalDate dataCriacao;
    private Integer status;

    private List<TransacaoBasicaResponseDTO> transacoes;
    private List<MetaBasicaResponseDTO> metas;

    public UsuarioResponseDTO(Integer id, String nome, String email, LocalDate dataCriacao, Integer status, List<TransacaoBasicaResponseDTO> transacoes, List<MetaBasicaResponseDTO> metas) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.transacoes = transacoes;
        this.metas = metas;
    }

    public UsuarioResponseDTO() {}


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<TransacaoBasicaResponseDTO> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<TransacaoBasicaResponseDTO> transacoes) {
        this.transacoes = transacoes;
    }

    public List<MetaBasicaResponseDTO> getMetas() {
        return metas;
    }

    public void setMetas(List<MetaBasicaResponseDTO> metas) {
        this.metas = metas;
    }
}

