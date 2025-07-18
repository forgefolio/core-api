package com.forgefolio.api.domain.model.portfolio;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.shared.Money;
import com.forgefolio.api.domain.model.shared.Quantity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public class Entry {

    private final Id id;
    private final Portfolio portfolio;
    private final Asset asset;

    private final ZonedDateTime date;

    private Type type;
    private Quantity amount;
    private Money unitPrice;

    public Entry(Portfolio portfolio, Asset asset, ZonedDateTime date, Type type, BigDecimal amount, BigDecimal unitPrice) {
        if (date == null)
            throw new BadArgumentException(ErrorCode.DATE_NULL);
        if (type == null)
            throw new BadArgumentException(ErrorCode.ENTRY_TYPE_NULL);

        this.id = new Id();
        this.portfolio = portfolio;
        this.asset = asset;
        this.date = date;
        this.type = type;
        this.amount = new Quantity(amount);
        this.unitPrice = new Money(unitPrice);
    }

    public Entry(UUID id, ZonedDateTime zonedDateTime, Type type, BigDecimal amount, BigDecimal unitPrice) {
        this.id = new Id(id);
        this.portfolio = null;
        this.asset = null;
        this.date = zonedDateTime;
        this.type = type;
        this.amount = new Quantity(amount);
        this.unitPrice = new Money(unitPrice);
    }

    public enum Type {
        BUY,
        SELL,
    }
}
