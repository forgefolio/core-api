package com.forgefolio.api.application.port.in.portfolio.command;

import com.forgefolio.api.domain.model.portfolio.Goal;

import java.math.BigDecimal;

public class CreateGoalCommand {

    private final String portfolioId;
    private final String assetId;

    private final Goal.Type type;
    private final BigDecimal amount;
    private final BigDecimal percentage;
    private final BigDecimal value;

    public CreateGoalCommand(String portfolioId, String assetId, Goal.Type type, BigDecimal amount, BigDecimal percentage, BigDecimal value) {
        this.portfolioId = portfolioId;
        this.assetId = assetId;
        this.type = type;
        this.amount = amount;
        this.percentage = percentage;
        this.value = value;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getAssetId() {
        return assetId;
    }

    public Goal.Type getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public BigDecimal getValue() {
        return value;
    }
}
