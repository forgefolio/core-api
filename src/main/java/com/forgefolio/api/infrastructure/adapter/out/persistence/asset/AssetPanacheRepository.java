package com.forgefolio.api.infrastructure.adapter.out.persistence.asset;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AssetPanacheRepository implements PanacheRepositoryBase<AssetEntity, UUID> {
    public Uni<AssetEntity> findByTicker(String ticker) {
        return find("ticker", ticker).firstResult();
    }
}
