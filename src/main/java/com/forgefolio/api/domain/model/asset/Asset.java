package com.forgefolio.api.domain.model.asset;

import com.forgefolio.api.domain.model.shared.Id;

import java.util.UUID;

public class Asset {

    private final Id id;

    private final Ticker ticker;
    private final String name;

    public Asset(String ticker, String name) {
        this.id = new Id();
        this.ticker = new Ticker(ticker);
        this.name = name;
    }

    public Asset(UUID id, String ticker, String name) {
        this.id = new Id(id);
        this.ticker = new Ticker(ticker);
        this.name = name;
    }

    public Id getId() {
        return id;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }
}
