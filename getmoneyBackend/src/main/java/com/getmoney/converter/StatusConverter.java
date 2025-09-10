package com.getmoney.converter;

import com.getmoney.enums.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Status status) {
        return status != null ? status.getCodigo() : null;
    }

    @Override
    public Status convertToEntityAttribute(Integer codigo) {
        if (codigo == null) {
            return Status.INATIVO;
        }
        for (Status status : Status.values()) {
            if (status.getCodigo().equals(codigo)) {
                return status;
            }
        }
        return Status.INATIVO;
    }
}
