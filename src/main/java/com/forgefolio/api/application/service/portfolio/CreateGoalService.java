package com.forgefolio.api.application.service.portfolio;

import com.forgefolio.api.application.port.in.portfolio.CreateGoalUseCase;
import com.forgefolio.api.application.port.in.portfolio.command.CreateGoalCommand;
import com.forgefolio.api.application.port.in.portfolio.response.GoalResponse;
import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.application.port.out.persistence.asset.AssetRepository;
import com.forgefolio.api.application.port.out.persistence.portfolio.GoalRepository;
import com.forgefolio.api.application.port.out.persistence.portfolio.PortfolioRepository;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.exception.NotFoundException;
import com.forgefolio.api.domain.model.portfolio.Goal;
import com.forgefolio.api.domain.model.shared.Id;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateGoalService implements CreateGoalUseCase {

    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;
    private final GoalRepository goalRepository;
    private final PersistenceContextExecutor persistenceContextExecutor;

    public CreateGoalService(PortfolioRepository portfolioRepository, AssetRepository assetRepository, GoalRepository goalRepository, PersistenceContextExecutor persistenceContextExecutor) {
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
        this.goalRepository = goalRepository;
        this.persistenceContextExecutor = persistenceContextExecutor;
    }

    @Override
    public Uni<GoalResponse> createGoal(CreateGoalCommand command) {
        Id portfolioId = new Id(command.getPortfolioId());
        Id assetId = new Id(command.getAssetId());

        return persistenceContextExecutor.runInTransaction(() ->
                portfolioRepository.findById(portfolioId)
                        .onItem().ifNull().failWith(() -> new NotFoundException(ErrorCode.PORTFOLIO_NOT_FOUND))
                        .flatMap(portfolio ->
                                assetRepository.findById(assetId)
                                        .onItem().ifNull().failWith(() -> new NotFoundException(ErrorCode.ASSET_NOT_FOUND))
                                        .flatMap(asset -> {

                                            Goal goal = new Goal(portfolio, asset, command.getType(),
                                                    command.getAmount(), command.getPercentage(), command.getValue());

                                            return goalRepository.save(goal)
                                                    .replaceWith(goal)
                                                    .map(GoalResponse::new);
                                        })
                        )
        );
    }
}
