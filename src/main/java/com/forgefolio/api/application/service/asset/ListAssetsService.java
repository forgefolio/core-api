package com.forgefolio.api.application.service.asset;

import com.forgefolio.api.application.port.in.asset.ListAssetsUseCase;
import com.forgefolio.api.application.port.in.asset.response.AssetResponse;
import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.application.port.out.persistence.asset.AssetRepository;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.asset.AssetPrice;
import com.forgefolio.api.domain.pagination.PageResponse;
import com.forgefolio.api.domain.pagination.asset.ListAssetsCommand;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ListAssetsService implements ListAssetsUseCase {

    private final AssetRepository assetRepository;
    private final PersistenceContextExecutor persistenceContextExecutor;

    public ListAssetsService(AssetRepository assetRepository, PersistenceContextExecutor persistenceContextExecutor) {
        this.assetRepository = assetRepository;
        this.persistenceContextExecutor = persistenceContextExecutor;
    }

    @Override
    public Uni<PageResponse<AssetResponse>> getAssets(ListAssetsCommand command) {
        return persistenceContextExecutor.runInSession(() -> {
            return assetRepository.findAssetsWithCurrentPrices(command)
                    .map(pageResponse -> new PageResponse<>(
                            pageResponse,
                            (pairs) -> {
                                Asset asset = pairs.first();
                                AssetPrice assetPrice = pairs.second();

                                return new AssetResponse(asset, assetPrice);
                            }
                    ));
        });
    }
}
