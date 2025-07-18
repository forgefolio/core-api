package com.forgefolio.api.domain.model.portfolio;

import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.shared.Money;
import com.forgefolio.api.domain.model.shared.Percentage;
import com.forgefolio.api.domain.model.shared.Quantity;

public class Goal {

    private final Id id;
    private final Portfolio portfolio;
    private final Asset asset;

    private Type type;
    private Quantity amount;
    private Percentage percentage;
    private Money value;

    public Goal(Id id, Portfolio portfolio, Asset asset) {
        this.id = id;
        this.portfolio = portfolio;
        this.asset = asset;
    }

    public enum Type {
        PERCENTAGE,
        AMOUNT,
        VALUE,
    }
}
