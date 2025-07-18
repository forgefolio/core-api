package com.forgefolio.api.domain.model.asset;

import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.shared.Money;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public class AssetPrice {

    private final Id id;
    private final Asset asset;
    private final ZonedDateTime date;

    private final Money price;

    public AssetPrice(Asset asset, ZonedDateTime date, BigDecimal price) {
        this.id = new Id();
        this.asset = asset;
        this.date = date;

        this.price = new Money(price);
    }

    public AssetPrice(UUID id, Asset asset, BigDecimal price, ZonedDateTime date) {
        this.id = new Id(id);
        this.asset = asset;
        this.date = date;

        this.price = new Money(price);
    }

    public AssetPrice(AssetPrice other, Asset newAsset) {
        this.id = other.id;
        this.asset = newAsset;
        this.date = other.date;
        this.price = other.price;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Money getPrice() {
        return price;
    }

    public Asset getAsset() {
        return asset;
    }
}
