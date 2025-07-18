package com.forgefolio.api.domain.model.portfolio;

import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.user.User;

public class Portfolio {

    private final Id id;
    private final User user;

    private final PortfolioName name;

    public Portfolio(User user, String name) {
        this.id = new Id();

        this.user = user;
        this.name = new PortfolioName(name);
    }

    public Portfolio(Id id, User user, PortfolioName name) {
        this.id = id;
        this.user = user;
        this.name = name;
    }
}
