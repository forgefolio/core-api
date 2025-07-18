package com.forgefolio.api.infrastructure.adapter.in.rest.scheduler;

import com.forgefolio.api.application.port.in.asset.FetchAssetPricesUseCase;
import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RouteBase(path = "scheduler/asset-prices", produces = ReactiveRoutes.APPLICATION_JSON)
public class AssetPriceController {

    private final FetchAssetPricesUseCase fetchAssetPricesUseCase;

    public AssetPriceController(FetchAssetPricesUseCase fetchAssetPricesUseCase) {
        this.fetchAssetPricesUseCase = fetchAssetPricesUseCase;
    }

    @Route(methods = Route.HttpMethod.POST, path = "fetch")
    public Uni<Void> fetchAssetPrices() {
        return fetchAssetPricesUseCase.fetchAssetPrices();
    }

}
