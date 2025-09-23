package com.getmoney.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Integer id;

    @Column(name="categoria_nome")
    private String nome;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "categoria_tipo")
    private CategoriaTipo tipo;


    @Column(name="categoria_status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Transacao> transacoes;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = status.ATIVO;
        }
    }

    public Categoria() {
    }

    public Categoria(Integer id, String nome, CategoriaTipo tipo, Status status,Usuario usuario, List<Transacao> transacoes) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.status = status;
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

    public CategoriaTipo getTipo() {
        return tipo;
    }

    public void setTipo(CategoriaTipo tipo) {
        this.tipo = tipo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
