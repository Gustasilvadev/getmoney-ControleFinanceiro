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

    @NotNull(message = "O status é obrigatório")
    private Integer status;

    @NotNull(message = "A data é obrigatória")
    @FutureOrPresent(message = "A data deve ser hoje ou uma data futura")
    private LocalDate data;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer usuarioId;

    public MetaRequestDTO() {
    }

    public MetaRequestDTO(String nome, BigDecimal valorAlvo, Integer status, LocalDate data, Integer usuarioId) {
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.status = status;
        this.data = data;
        this.usuarioId = usuarioId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
