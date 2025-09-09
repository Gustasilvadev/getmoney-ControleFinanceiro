package com.getmoney.dto.request;

import com.getmoney.enums.Status;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetaRequestUpdateDTO {

    @NotBlank(message = "O nome da meta é obrigatório")
    @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
    private String nome;

    @NotNull(message = "O valor alvo é obrigatório")
    @Positive(message = "O valor alvo deve ser maior que zero")
    private BigDecimal valorAlvo;

    @NotNull(message = "O status é obrigatório")
    private Status status;

    @NotNull(message = "A data é obrigatória")
    @FutureOrPresent(message = "A data deve ser hoje ou uma data futura")
    private LocalDate data;


    public MetaRequestUpdateDTO() {
    }

    public MetaRequestUpdateDTO(String nome, BigDecimal valorAlvo, Status status, LocalDate data) {
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
