package com.getmoney.dto.request;

public class AlterarSenhaRequestDTO {
    private String senhaAtual;
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
