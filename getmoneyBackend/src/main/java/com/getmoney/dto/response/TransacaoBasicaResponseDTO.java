package com.getmoney.dto.response;

import com.getmoney.entity.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoBasicaResponseDTO {

    private Integer id;
    private BigDecimal valor;
    private String descricao;
    private LocalDate data;

    private Integer categoriaId;
    private String categoriaNome;
    private String categoriaTipo;

    public TransacaoBasicaResponseDTO() {
    }

    public TransacaoBasicaResponseDTO(Integer id, BigDecimal valor, String descricao, LocalDate data, Integer categoriaId, String categoriaNome, String categoriaTipo) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.data = data;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.categoriaTipo = categoriaTipo;
    }


    public TransacaoBasicaResponseDTO(Transacao transacao) {
        this.id = transacao.getId();
        this.valor = transacao.getValor();
        this.descricao = transacao.getDescricao();
        this.data = transacao.getData();
        if (transacao.getCategoria() != null) {
            this.categoriaId = transacao.getCategoria().getId();
            this.categoriaNome = transacao.getCategoria().getNome();
            this.categoriaTipo = transacao.getCategoria().getTipo().toString();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCategoriaTipo() {
        return categoriaTipo;
    }

    public void setCategoriaTipo(String categoriaTipo) {
        this.categoriaTipo = categoriaTipo;
    }
}


