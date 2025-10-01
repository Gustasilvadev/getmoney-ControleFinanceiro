package com.getmoney.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioRequestDTO {

    @NotBlank(message = "O nome é obrigatória")
    @Size(min = 5, max = 20, message = "O nome deve ter entre 5 e 20 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;


    public UsuarioRequestDTO() {}

    public UsuarioRequestDTO(String nome, String email) {
        this.nome = nome;
        this.email = email;
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

}
