package com.getmoney.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoriaTipo {
    Despesa(0),
    Receita(1);

    private final int codigo;

    CategoriaTipo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static CategoriaTipo fromCodigo(int codigo) {
        for (CategoriaTipo tipo : values()) {
            if (tipo.codigo == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}
