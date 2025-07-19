package com.forgefolio.api.infrastructure.adapter.in.rest.portfolio;

import com.forgefolio.api.application.port.in.portfolio.CreatePortfolioUserCase;
import com.forgefolio.api.application.port.in.portfolio.command.CreatePortfolioCommand;
import com.forgefolio.api.application.port.in.portfolio.response.PortfolioResponse;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PortfolioController {

    private final CreatePortfolioUserCase createPortfolioUserCase;

    public PortfolioController(CreatePortfolioUserCase createPortfolioUserCase) {
        this.createPortfolioUserCase = createPortfolioUserCase;
    }

    @Route(methods = Route.HttpMethod.POST, path = "/portfolios")
    public Uni<PortfolioResponse> createPortfolio(
            RoutingContext ctx,
            @Body CreatePortfolioCommand command
    ) {
        return this.createPortfolioUserCase.createPortfolio(command)
                .onItem().invoke(portfolioResponse -> {
                    String location = "/portfolios/" + portfolioResponse.getId();


                    ctx.response()
                            .setStatusCode(201)
                            .putHeader("Location", location)
                            .end(Json.encode(portfolioResponse));
                });
    }

}
