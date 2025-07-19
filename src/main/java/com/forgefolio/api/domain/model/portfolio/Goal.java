package com.forgefolio.api.domain.model.portfolio;

import com.forgefolio.api.domain.exception.BadArgumentException;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.shared.Money;
import com.forgefolio.api.domain.model.shared.Percentage;
import com.forgefolio.api.domain.model.shared.Quantity;

import java.math.BigDecimal;

public class Goal {

    private final Id id;
    private final Portfolio portfolio;
    private final Asset asset;

    private Type type;
    private Quantity amount;
    private Percentage percentage;
    private Money value;

    public Goal(Portfolio portfolio, Asset asset, Type type, BigDecimal amount, BigDecimal percentage, BigDecimal value) {
        if (type == null)
            throw new BadArgumentException(ErrorCode.GOAL_TYPE_NULL);

        this.id = new Id();
        this.portfolio = portfolio;
        this.asset = asset;
        this.type = type;
        this.amount = type.equals(Type.AMOUNT) ? new Quantity(amount) : null;
        this.percentage = type.equals(Type.PERCENTAGE) ? new Percentage(percentage) : null;
        this.value = type.equals(Type.VALUE) ? new Money(value) : null;
    }

    public enum Type {
        PERCENTAGE,
        AMOUNT,
        VALUE,
    }

    public Id getId() {
        return id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public Asset getAsset() {
        return asset;
    }

    public Type getType() {
        return type;
    }

    public Quantity getAmount() {
        return amount;
    }

    public Percentage getPercentage() {
        return percentage;
    }

    public Money getValue() {
        return value;
    }
}
