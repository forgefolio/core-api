package com.forgefolio.api.infrastructure.adapter.out.persistence.asset;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AssetPricePanacheRepository implements PanacheRepositoryBase<AssetPriceEntity, UUID> {
}
