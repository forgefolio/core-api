package com.forgefolio.api.domain.model.asset;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;

import java.util.Objects;

public class Ticker {
    private final String value;

    public Ticker(String value) {
        if (value == null || value.isBlank())
            throw new BadArgumentException(ErrorCode.TICKER_EMPTY);

        this.value = value.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ticker ticker)) return false;

        return Objects.equals(value, ticker.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
