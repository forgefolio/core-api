package com.forgefolio.api.application.port.in.asset.response;

import com.forgefolio.api.domain.model.asset.AssetPrice;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class AssetPriceResponse {

    private final ZonedDateTime date;
    private final BigDecimal price;

    public AssetPriceResponse(AssetPrice price) {
        this.date = price.getDate();
        this.price = price.getPrice().getValue();
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
