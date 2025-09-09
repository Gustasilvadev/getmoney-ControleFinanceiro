package com.getmoney.enums;

public enum Status {
    INATIVO(0),
    ATIVO(1);

    private final Integer codigo;

    Status(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public static Status fromCodigo(Integer codigo) {
        for (Status status : values()) {
            if (status.getCodigo().equals(codigo)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido para Status: " + codigo);
    }
}