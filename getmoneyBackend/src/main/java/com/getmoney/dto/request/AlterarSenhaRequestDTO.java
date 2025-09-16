package com.getmoney.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AlterarSenhaRequestDTO {

    @NotBlank(message = "Senha atual é obrigatória")
    private String senhaAtual;

    @NotBlank(message = "Senha é obrigatório")
    @Size(min = 3, message = "A senha deve ter no minimo 3 caracteres")
    private String novaSenha;

    public AlterarSenhaRequestDTO() {
    }

    public AlterarSenhaRequestDTO(String novaSenha, String senhaAtual) {
        this.novaSenha = novaSenha;
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
