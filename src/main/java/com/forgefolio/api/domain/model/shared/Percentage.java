package com.forgefolio.api.domain.model.shared;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;

import java.math.BigDecimal;

public class Percentage {

    private BigDecimal value;

    public Percentage(BigDecimal percentage) {
        if (percentage == null)
            throw new BadArgumentException(ErrorCode.PERCENTAGE_NULL);

        this.value = percentage;
    }

    public BigDecimal getValue() {
        return value;
    }
}
