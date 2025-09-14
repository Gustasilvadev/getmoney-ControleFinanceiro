package com.getmoney.dto.response;

import com.getmoney.entity.Transacao;
import com.getmoney.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransacaoResponseDTO {

    private Integer id;
    private BigDecimal valor;
    private String descricao;
    private LocalDate data;
    private Status status;

    private Integer usuarioId;
    private Integer categoriaId;
    private List<MetaBasicaResponseDTO> metasId;


    public TransacaoResponseDTO(Transacao transacao) {
        this.id = transacao.getId();
        this.valor = transacao.getValor();
        this.descricao = transacao.getDescricao();
        this.data = transacao.getData();
        this.status = transacao.getStatus();
        this.usuarioId = transacao.getUsuario().getId();
        this.categoriaId = transacao.getCategoria().getId();
        this.metasId = metasId != null ? metasId : new ArrayList<>();
    }

    public TransacaoResponseDTO() {
        this.metasId = new ArrayList<>();
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public List<MetaBasicaResponseDTO> getMetasId() {
        return metasId;
    }

    public void setMetasId(List<MetaBasicaResponseDTO> metasId) {
        this.metasId = metasId;
    }
}
