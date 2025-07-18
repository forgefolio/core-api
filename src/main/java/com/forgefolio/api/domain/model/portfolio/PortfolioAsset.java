package com.forgefolio.api.domain.model.portfolio;

import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.shared.Quantity;

public class PortfolioAsset {

    private final Portfolio portfolio;
    private final Asset asset;

    private Quantity amount;

    public PortfolioAsset(Portfolio portfolio, Asset asset) {
        this.portfolio = portfolio;
        this.asset = asset;
    }
}
