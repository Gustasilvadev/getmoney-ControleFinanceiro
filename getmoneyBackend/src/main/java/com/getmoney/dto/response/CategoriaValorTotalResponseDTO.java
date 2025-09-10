package com.getmoney.dto.response;

import java.math.BigDecimal;

public class CategoriaValorTotalResponseDTO {

    private Integer categoriaId;
    private String categoriaNome;
    private BigDecimal valorTotal;

    public CategoriaValorTotalResponseDTO(Integer categoriaId, String categoriaNome, BigDecimal valorTotal) {
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.valorTotal = valorTotal;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
