package com.forgefolio.api.infrastructure.adapter.in.rest.portfolio;

import com.forgefolio.api.application.port.in.portfolio.CreatePortfolioUserCase;
import com.forgefolio.api.application.port.in.portfolio.command.CreatePortfolioCommand;
import com.forgefolio.api.application.port.in.portfolio.response.PortfolioResponse;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PortfolioResource {

    private final CreatePortfolioUserCase createPortfolioUserCase;

    public PortfolioResource(CreatePortfolioUserCase createPortfolioUserCase) {
        this.createPortfolioUserCase = createPortfolioUserCase;
    }

    @Route(methods = Route.HttpMethod.POST, path = "/portfolios")
    public Uni<PortfolioResponse> createPortfolio(
            @Body CreatePortfolioCommand command
    ) {
        return this.createPortfolioUserCase.createPortfolio(command);
    }

}
