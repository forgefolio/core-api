package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.asset;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PortfolioAssetPanacheRepository implements PanacheRepositoryBase<PortfolioAssetEntity, PortfolioAssetEntity.ID> {
}

