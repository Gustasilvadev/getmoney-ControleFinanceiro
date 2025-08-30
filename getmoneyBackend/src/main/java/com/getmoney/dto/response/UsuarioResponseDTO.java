package com.getmoney.dto.response;

import com.getmoney.entity.Usuario;

import java.time.LocalDate;

public class UsuarioResponseDTO {

    private Integer id;

    private String nome;

    private String email;

    private LocalDate dataCriacao;

    private Integer status;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.dataCriacao = usuario.getDataCriacao();
        this.status = usuario.getStatus();
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
}

