package com.getmoney.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetaRequestDTO {

    @NotBlank(message = "O nome da meta é obrigatório")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @NotNull(message = "O valor alvo é obrigatório")
    @Positive(message = "O valor alvo deve ser maior que zero")
    private BigDecimal valorAlvo;

    @NotNull(message = "A data é obrigatória")
    @FutureOrPresent(message = "A data deve ser hoje ou uma data futura")
    private LocalDate data;


    public MetaRequestDTO() {
    }

    public MetaRequestDTO(String nome, BigDecimal valorAlvo, LocalDate data) {
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorAlvo() {
        return valorAlvo;
    }

    public void setValorAlvo(BigDecimal valorAlvo) {
        this.valorAlvo = valorAlvo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

}
