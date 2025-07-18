package com.forgefolio.api.domain.model.shared;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;

import java.math.BigDecimal;

public class Money {

    private final BigDecimal value;

    public Money(BigDecimal value) {
        if (value == null)
            throw new BadArgumentException(ErrorCode.MONEY_NULL);

        this.value = value;
    }
}
