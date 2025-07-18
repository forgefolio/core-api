package com.forgefolio.api.infrastructure.adapter.in.rest.asset;

import com.forgefolio.api.application.port.in.asset.ListAssetsUseCase;
import com.forgefolio.api.application.port.in.asset.response.AssetResponse;
import com.forgefolio.api.domain.pagination.PageResponse;
import com.forgefolio.api.domain.pagination.asset.ListAssetsCommand;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
@RouteBase(path = "assets", produces = ReactiveRoutes.APPLICATION_JSON)
public class AssetResource {

    private final ListAssetsUseCase listAssetsUseCase;

    public AssetResource(ListAssetsUseCase listAssetsUseCase) {
        this.listAssetsUseCase = listAssetsUseCase;
    }

    @Route(methods = Route.HttpMethod.GET)
    public Uni<PageResponse<AssetResponse>> getAssets(
            @Param ListAssetsCommand query
    ) {
        return listAssetsUseCase.getAssets(query);
    }

}
