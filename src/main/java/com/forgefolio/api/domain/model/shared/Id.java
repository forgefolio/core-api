package com.forgefolio.api.domain.model.shared;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;

import java.util.Objects;
import java.util.UUID;

public class Id {

    private final UUID value;

    public Id() {
        this.value = UUID.randomUUID();
    }

    public Id(UUID value) {
        this.value = value;
    }

    public Id(String string) {
        if (string == null || string.isEmpty())
            throw new BadArgumentException(ErrorCode.ID_EMPTY);

        try {
            this.value = UUID.fromString(string);
        } catch (Exception e) {
            throw new BadArgumentException(ErrorCode.ID_INVALID);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Id id)) return false;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public UUID getValue() {
        return value;
    }
}
