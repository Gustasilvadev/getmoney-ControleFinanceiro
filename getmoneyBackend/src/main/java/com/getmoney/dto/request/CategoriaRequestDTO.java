package com.getmoney.dto.request;

import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoriaRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotNull(message = "O tipo é obrigatório")
    private CategoriaTipo tipo;

    @NotNull(message = "O status é obrigatório")
    private Integer  status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

