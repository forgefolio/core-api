package com.forgefolio.api.domain.model.portfolio;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;

public class PortfolioName {

    private final String value;

    public PortfolioName(String value) {
        if (value == null || value.trim().isEmpty())
            throw new BadArgumentException(ErrorCode.PORTFOLIO_NAME_EMPTY);

        if (value.length() > 255)
            throw new BadArgumentException(ErrorCode.PORTFOLIO_NAME_TOO_LONG);

        this.value = value;
    }

}
