package com.forgefolio.api.infrastructure.adapter.in.rest.scheduler;

import com.forgefolio.api.application.port.in.asset.FetchAssetPricesUseCase;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AssetPriceSchedulerResource {

    private final FetchAssetPricesUseCase fetchAssetPricesUseCase;

    public AssetPriceSchedulerResource(FetchAssetPricesUseCase fetchAssetPricesUseCase) {
        this.fetchAssetPricesUseCase = fetchAssetPricesUseCase;
    }

    @Route(methods = Route.HttpMethod.POST, path = "/scheduler/asset-prices/fetch")
    public Uni<Void> fetchAssetPrices() {
        return fetchAssetPricesUseCase.fetchAssetPrices();
    }

}
