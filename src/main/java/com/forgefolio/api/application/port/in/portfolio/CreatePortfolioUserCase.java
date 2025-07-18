package com.forgefolio.api.application.port.in.portfolio;

import com.forgefolio.api.application.port.in.portfolio.command.CreatePortfolioCommand;
import com.forgefolio.api.application.port.in.portfolio.response.PortfolioResponse;
import io.smallrye.mutiny.Uni;

public interface CreatePortfolioUserCase {

    Uni<PortfolioResponse> createPortfolio(CreatePortfolioCommand command);

}
