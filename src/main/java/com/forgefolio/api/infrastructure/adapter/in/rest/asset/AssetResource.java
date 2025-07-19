package com.forgefolio.api.infrastructure.adapter.in.rest.asset;

import com.forgefolio.api.application.port.in.asset.ListAssetsUseCase;
import com.forgefolio.api.application.port.in.asset.response.AssetResponse;
import com.forgefolio.api.domain.pagination.PageResponse;
import com.forgefolio.api.domain.pagination.asset.ListAssetsCommand;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


@ApplicationScoped
public class AssetResource {

    private final ListAssetsUseCase listAssetsUseCase;

    public AssetResource(ListAssetsUseCase listAssetsUseCase) {
        this.listAssetsUseCase = listAssetsUseCase;
    }

    @Route(methods = Route.HttpMethod.GET, path = "/assets")
    public Uni<PageResponse<AssetResponse>> getAssets(RoutingContext rc) {
        int page = Integer.parseInt(rc.request().getParam("page") != null ? rc.request().getParam("page") : "0");
        int size = Integer.parseInt(rc.request().getParam("size") != null ? rc.request().getParam("size") : "10");
        List<String> sort = rc.request().params().getAll("sort");
        String ticker = rc.request().getParam("ticker");

        ListAssetsCommand command = new ListAssetsCommand(page, size, sort, ticker);

        return listAssetsUseCase.getAssets(command);
    }

}
