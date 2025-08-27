package com.getmoney.dto.response;

import com.getmoney.entity.Categoria;
import com.getmoney.enums.CategoriaTipo;
public class CategoriaResponseDTO {

    private Integer id;

    private String nome;

    private CategoriaTipo tipo;

    private Integer status;

    public CategoriaResponseDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.tipo = categoria.getTipo();
        this.status = categoria.getStatus();
    }

    public CategoriaResponseDTO() {}

    public CategoriaResponseDTO(Integer id, String nome, CategoriaTipo tipo, Integer status) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.status = status;
    }

    // Getters e Setters
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
}