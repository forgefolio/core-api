package com.forgefolio.api.domain.model.shared;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;

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

        this.value = UUID.fromString(string);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public UUID getValue() {
        return value;
    }
}
