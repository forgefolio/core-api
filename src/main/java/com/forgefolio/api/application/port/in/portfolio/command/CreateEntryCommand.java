package com.forgefolio.api.application.port.in.portfolio.command;

import com.forgefolio.api.domain.model.portfolio.Entry;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class CreateEntryCommand {

    private final String portfolioId;
    private final String assetId;
    private final ZonedDateTime date;

    private final Entry.Type type;
    private final BigDecimal amount;
    private final BigDecimal unitPrice;

    public CreateEntryCommand(String portfolioId, String assetId, ZonedDateTime date, Entry.Type type, BigDecimal amount, BigDecimal unitPrice) {
        this.portfolioId = portfolioId;
        this.assetId = assetId;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.unitPrice = unitPrice;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getAssetId() {
        return assetId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Entry.Type getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}
