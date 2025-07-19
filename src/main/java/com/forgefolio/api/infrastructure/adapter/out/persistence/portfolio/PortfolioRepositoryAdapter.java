package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio;

import com.forgefolio.api.application.port.out.portfolio.PortfolioRepository;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.portfolio.Portfolio;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.shared.Quantity;
import com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.asset.PortfolioAssetEntity;
import com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.asset.PortfolioAssetPanacheRepository;
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
    public Uni<Void> save(Portfolio portfolio) {
        PortfolioEntity entity = new PortfolioEntity(portfolio);
        return portfolioRepo.persist(entity).replaceWithVoid();
    }

    @Override
    public Uni<Portfolio> findById(Id id) {
        return portfolioRepo.findById(id.getValue())
                .map(PortfolioEntity::toDomain);
    }

    @Override
    public Uni<Void> increaseAssetAmount(Portfolio portfolio, Asset asset, Quantity quantity) {
        UUID portfolioId = portfolio.getId().getValue();
        UUID assetId = asset.getId().getValue();

        return assetRepo.findById(new PortfolioAssetEntity.ID(portfolioId, assetId))
                .flatMap(existing -> {
                    existing.incrementAmount(quantity);
                    return assetRepo.persist(existing).replaceWithVoid();
                })
                .onItem().ifNull().switchTo(() -> {
                    PortfolioAssetEntity entity = new PortfolioAssetEntity(portfolio, asset, quantity);
                    return assetRepo.persist(entity).replaceWithVoid();
                });
    }

    @Override
    public Uni<Void> decreaseAssetAmount(Portfolio portfolio, Asset asset, Quantity quantity) {
        UUID portfolioId = portfolio.getId().getValue();
        UUID assetId = asset.getId().getValue();

        return assetRepo.findById(new PortfolioAssetEntity.ID(portfolioId, assetId))
                .flatMap(existing -> {
                    existing.decrementAmount(quantity);
                    return assetRepo.persist(existing).replaceWithVoid();
                })
                .onItem().ifNull().switchTo(() -> {
                    PortfolioAssetEntity entity = PortfolioAssetEntity.negative(portfolio, asset, quantity);
                    return assetRepo.persist(entity).replaceWithVoid();
                });
    }
}
