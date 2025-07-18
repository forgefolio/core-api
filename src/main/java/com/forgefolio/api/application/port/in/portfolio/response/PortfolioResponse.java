package com.forgefolio.api.application.port.in.portfolio.response;

import com.forgefolio.api.domain.model.portfolio.Portfolio;

public class PortfolioResponse {

    private final String id;
    private final String userId;
    private final String name;


    public PortfolioResponse(Portfolio portfolio) {
        this.id = portfolio.getId().toString();
        this.userId = portfolio.getUser().getId().toString();
        this.name = portfolio.getName().getValue();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }
}
