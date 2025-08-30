package com.getmoney.dto.response;

import com.getmoney.entity.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoResponseDTO {

    private Integer id;

    private BigDecimal valor;

    private String descricao;

    private LocalDate data;

    private Integer status;

    private Integer usuarioId;

    private Integer categoriaId;

    private String categoriaNome;

    private String categoriaTipo;


    public TransacaoResponseDTO(Transacao transacao) {
        this.id = transacao.getId();
        this.valor = transacao.getValor();
        this.descricao = transacao.getDescricao();
        this.data = transacao.getData();
        this.status = transacao.getStatus();
        this.usuarioId = transacao.getUsuario().getId();
        this.categoriaId = transacao.getCategoria().getId();
        this.categoriaNome = transacao.getCategoria().getNome();
        this.categoriaTipo = transacao.getCategoria().getTipo().name();
    }

    public TransacaoResponseDTO() {}


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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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
