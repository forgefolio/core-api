package com.forgefolio.api.application.port.in.portfolio.command;


public record CreatePortfolioCommand(
        String userId,
        String name
) {
}
