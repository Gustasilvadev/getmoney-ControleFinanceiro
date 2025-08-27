package com.getmoney.dto.request;

import com.getmoney.enums.CategoriaTipo;

public class CategoriaRequestDTO {

    private String nome;

    private CategoriaTipo tipo;

    public CategoriaRequestDTO() {}

    public CategoriaRequestDTO(String nome, CategoriaTipo tipo) {
        this.nome = nome;
        this.tipo = tipo;
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
}

