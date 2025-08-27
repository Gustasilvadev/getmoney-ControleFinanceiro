package com.getmoney.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getmoney.enums.CategoriaTipo;
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
    private Integer status;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<Transacao> transacoes;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = 1; // Ao criar a categoria automaticamente --> 1 = ativo
        }
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
