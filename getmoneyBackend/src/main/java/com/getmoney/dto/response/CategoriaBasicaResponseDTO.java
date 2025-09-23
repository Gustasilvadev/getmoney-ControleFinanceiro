package com.getmoney.dto.response;

import com.getmoney.entity.Categoria;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;


public class CategoriaBasicaResponseDTO {
    private Integer id;
    private String nome;
    private CategoriaTipo tipo;
    private Status status;

    private Integer usuarioId;

    public CategoriaBasicaResponseDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.tipo = categoria.getTipo();
        this.status = categoria.getStatus();
        this.usuarioId = categoria.getUsuario().getId();
    }

    public CategoriaBasicaResponseDTO() {}


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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


}
