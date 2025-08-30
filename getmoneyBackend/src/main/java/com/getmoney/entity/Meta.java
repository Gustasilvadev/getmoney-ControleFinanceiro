package com.getmoney.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meta")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id")
    private Integer id;

    @Column(name = "meta_nome")
    private String nome;

    @Column(name = "meta_valor_alvo",  precision = 10, scale = 2)
    private BigDecimal valor_alvo;

    @Column(name = "meta_status")
    private Integer status;

    @Column(name = "meta_data")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany(mappedBy = "metas")
    private List<Transacao> transacoes = new ArrayList<>();


    public Meta() {
    }

    public Meta(Integer id, String nome, BigDecimal valor_alvo, Integer status, LocalDate data, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.valor_alvo = valor_alvo;
        this.status = status;
        this.data = data;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}