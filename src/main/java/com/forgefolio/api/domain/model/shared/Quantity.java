package com.forgefolio.api.domain.model.shared;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;

import java.math.BigDecimal;
public class Quantity {
    private BigDecimal value;

    public Quantity(BigDecimal amount) {
        if (amount == null)
            throw new BadArgumentException(ErrorCode.QUANTITY_NULL);

        this.value = amount;
    }

    public BigDecimal getValue() {
        return value;
    }
}
