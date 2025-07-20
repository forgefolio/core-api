package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio;

import com.forgefolio.api.application.port.out.portfolio.PortfolioRepository;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.exception.NotFoundException;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.portfolio.Portfolio;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.shared.Quantity;
import com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.asset.PortfolioAssetEntity;
import com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.asset.PortfolioAssetPanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class PortfolioRepositoryAdapter implements PortfolioRepository {

    private final PortfolioPanacheRepository portfolioRepo;
    private final PortfolioAssetPanacheRepository assetRepo;

    public PortfolioRepositoryAdapter(PortfolioPanacheRepository portfolioRepo, PortfolioAssetPanacheRepository assetRepo) {
        this.portfolioRepo = portfolioRepo;
        this.assetRepo = assetRepo;
    }

    @Override
    @WithTransaction
    public Uni<Void> save(Portfolio portfolio) {
        PortfolioEntity entity = new PortfolioEntity(portfolio);
        return portfolioRepo.persist(entity).replaceWithVoid();
    }

    @Override
    @WithSession
    public Uni<Portfolio> findById(Id id) {
        return portfolioRepo.findById(id.getValue())
                .onItem().ifNull().failWith(new NotFoundException(ErrorCode.PORTFOLIO_NOT_FOUND))
                .map(PortfolioEntity::toDomain);
    }

    @Override
    @WithTransaction
    public Uni<Void> increaseAssetAmount(Portfolio portfolio, Asset asset, Quantity quantity) {
        UUID portfolioId = portfolio.getId().getValue();
        UUID assetId = asset.getId().getValue();

        return assetRepo.findById(new PortfolioAssetEntity.ID(portfolioId, assetId))
                .onItem().ifNull().switchTo(() -> {
                    PortfolioAssetEntity entity = new PortfolioAssetEntity(portfolio, asset);
                    return assetRepo.persist(entity);
                })
                .flatMap(entity -> {
                    entity.incrementAmount(quantity);
                    return assetRepo.persist(entity).replaceWithVoid();
                });
    }

    @Override
    @WithTransaction
    public Uni<Void> decreaseAssetAmount(Portfolio portfolio, Asset asset, Quantity quantity) {
        UUID portfolioId = portfolio.getId().getValue();
        UUID assetId = asset.getId().getValue();

        return assetRepo.findById(new PortfolioAssetEntity.ID(portfolioId, assetId))
                .onItem().ifNull().switchTo(() -> {
                    PortfolioAssetEntity entity = new PortfolioAssetEntity(portfolio, asset);
                    return assetRepo.persist(entity);
                })
                .flatMap(entity -> {
                    entity.decrementAmount(quantity);
                    return assetRepo.persist(entity).replaceWithVoid();
                });
    }
}
