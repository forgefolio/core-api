package com.forgefolio.api.application.port.in.asset.response;

import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.asset.AssetPrice;

public class AssetResponse {

    private final String id;
    private final String ticker;
    private final String name;

    private final AssetPriceResponse currentPrice;

    public AssetResponse(Asset asset, AssetPrice currentPrice) {
        this.id = asset.getId().getValue().toString();
        this.ticker = asset.getTicker().getValue();
        this.name = asset.getName();

        this.currentPrice = new AssetPriceResponse(currentPrice);
    }

    public String getId() {
        return id;
    }

    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public AssetPriceResponse getCurrentPrice() {
        return currentPrice;
    }
}
