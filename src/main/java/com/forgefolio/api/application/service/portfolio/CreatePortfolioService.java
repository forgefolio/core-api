package com.forgefolio.api.application.service.portfolio;

import com.forgefolio.api.application.port.in.portfolio.CreatePortfolioUserCase;
import com.forgefolio.api.application.port.in.portfolio.command.CreatePortfolioCommand;
import com.forgefolio.api.application.port.in.portfolio.response.PortfolioResponse;
import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.application.port.out.persistence.portfolio.PortfolioRepository;
import com.forgefolio.api.application.port.out.persistence.user.UserRepository;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.exception.NotFoundException;
import com.forgefolio.api.domain.model.portfolio.Portfolio;
import com.forgefolio.api.domain.model.shared.Id;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreatePortfolioService implements CreatePortfolioUserCase {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final PersistenceContextExecutor persistenceContextExecutor;

    public CreatePortfolioService(UserRepository userRepository, PortfolioRepository portfolioRepository, PersistenceContextExecutor persistenceContextExecutor) {
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
        this.persistenceContextExecutor = persistenceContextExecutor;
    }

    @Override
    public Uni<PortfolioResponse> createPortfolio(CreatePortfolioCommand command) {
        Id userId = new Id(command.userId());

        return persistenceContextExecutor.runInTransaction(() ->
                userRepository.findById(userId)
                        .onItem().ifNull().failWith(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND))
                        .flatMap(user -> {
                            Portfolio portfolio = new Portfolio(user, command.name());

                            return portfolioRepository.save(portfolio)
                                    .replaceWith(new PortfolioResponse(portfolio));
                        }));
    }

}
