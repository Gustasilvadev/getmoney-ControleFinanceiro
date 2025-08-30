package com.getmoney.dto.response;

import com.getmoney.entity.Meta;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MetaResponseDTO {

    private Integer id;
    private String nome;
    private BigDecimal valor_alvo;
    private Integer status;
    private LocalDate data;
    private Integer usuarioId;
    private String usuarioNome;


    public MetaResponseDTO(Meta meta) {
        this.id = meta.getId();
        this.nome = meta.getNome();
        this.valor_alvo = meta.getValor_alvo();
        this.status = meta.getStatus();
        this.data = meta.getData();
        this.usuarioId = meta.getUsuario().getId();
        this.usuarioNome = meta.getUsuario().getNome();
    }

    public MetaResponseDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor_alvo() {
        return valor_alvo;
    }

    public void setValor_alvo(BigDecimal valor_alvo) {
        this.valor_alvo = valor_alvo;
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

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }
}
