package com.getmoney.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getmoney.enums.Status;
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
    private BigDecimal valorAlvo;


    @Column(name = "meta_status")
    private Status status;

    @Column(name = "meta_data")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany(mappedBy = "metas")
    @JsonIgnore
    private List<Transacao> transacoes = new ArrayList<>();


    //Inicializa os dados no momento da criacao
    @PrePersist
    public void prePersist() {
        this.data = LocalDate.now();
        if (this.status == null) {
            this.status = Status.ATIVO;
        }
    }

    public Meta() {
    }

    public Meta(Integer id, String nome, BigDecimal valorAlvo, Status status, LocalDate data, Usuario usuario, List<Transacao> transacoes) {
        this.id = id;
        this.nome = nome;
        this.valorAlvo = valorAlvo;
        this.status = status;
        this.data = data;
        this.usuario = usuario;
        this.transacoes = transacoes;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
}