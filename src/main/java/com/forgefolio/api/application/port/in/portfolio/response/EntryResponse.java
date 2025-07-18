package com.forgefolio.api.application.port.in.portfolio.response;


import com.forgefolio.api.domain.model.portfolio.Entry;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class EntryResponse {

    private final String id;
    private final String portfolioId;
    private final String assetId;

    private final ZonedDateTime date;

    private final Entry.Type type;
    private final BigDecimal amount;
    private final BigDecimal unitPrice;

    public EntryResponse(Entry entry) {
        this.id = entry.getId().toString();
        this.portfolioId = entry.getPortfolio().getId().toString();
        this.assetId = entry.getAsset().getId().toString();

        this.date = entry.getDate();

        this.type = entry.getType();
        this.amount = entry.getAmount().getValue();
        this.unitPrice = entry.getUnitPrice().getValue();
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
