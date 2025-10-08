package com.getmoney.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransacaoUpdateRequestDTO {

    @NotNull(message = "O valor é obrigatório")
    private BigDecimal valor;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 2, max = 100, message = "A descrição deve ter entre 2 e 100 caracteres")
    private String descricao;

    @NotNull(message = "A data é obrigatória")
    @PastOrPresent(message = "A data não pode ser futura")
    private LocalDate data;

    @NotNull(message = "O status é obrigatório")
    private Integer status;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Integer categoriaId;

    private List<Integer> metaId;

    public TransacaoUpdateRequestDTO() {}


    public TransacaoUpdateRequestDTO(BigDecimal valor, String descricao, LocalDate data, Integer status, Integer categoriaId, List<Integer> metaId) {
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.status = status;
        this.categoriaId = categoriaId;
        this.metaId = metaId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public List<Integer> getMetaId() {
        return metaId;
    }

    public void setMetaId(List<Integer> metaId) {
        this.metaId = metaId;
    }
}