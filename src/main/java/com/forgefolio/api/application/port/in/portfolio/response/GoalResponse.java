package com.forgefolio.api.application.port.in.portfolio.response;

import com.forgefolio.api.domain.model.portfolio.Goal;

import java.math.BigDecimal;

public class GoalResponse {

    private final String id;
    private final String portfolioId;
    private final String assetId;

    private final Goal.Type type;
    private final BigDecimal amount;
    private final BigDecimal percentage;
    private final BigDecimal value;

    public GoalResponse(Goal goal) {
        this.id = goal.getId().toString();
        this.portfolioId = goal.getPortfolio().getId().toString();
        this.assetId = goal.getAsset().getId().toString();

        this.type = goal.getType();
        this.amount = goal.getAmount() != null ? goal.getAmount().getValue() : null;
        this.percentage = goal.getPercentage() != null ? goal.getPercentage().getValue() : null;
        this.value = goal.getValue() != null ? goal.getValue().getValue() : null;
    }

    public String getId() {
        return id;
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
