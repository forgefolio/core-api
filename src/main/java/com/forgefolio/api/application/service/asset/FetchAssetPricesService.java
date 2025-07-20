package com.forgefolio.api.application.service.asset;

import com.forgefolio.api.application.port.in.asset.FetchAssetPricesUseCase;
import com.forgefolio.api.application.port.out.api.asset.AssetPriceProvider;
import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.application.port.out.persistence.asset.AssetRepository;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.asset.AssetPrice;
import com.forgefolio.api.domain.model.asset.Ticker;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class FetchAssetPricesService implements FetchAssetPricesUseCase {

    private final AssetPriceProvider assetPriceProvider;
    private final AssetRepository assetRepository;
    private final PersistenceContextExecutor persistenceContextExecutor;

    public FetchAssetPricesService(AssetPriceProvider assetPriceProvider, AssetRepository assetRepository, PersistenceContextExecutor persistenceContextExecutor) {
        this.assetPriceProvider = assetPriceProvider;
        this.assetRepository = assetRepository;
        this.persistenceContextExecutor = persistenceContextExecutor;
    }

    @Override
    public Uni<Void> fetchAssetPrices() {
        return assetPriceProvider.fetchAssetPrices()
                .flatMap(listWithOriginalAssets -> {
                    List<Asset> originalAssets = listWithOriginalAssets.stream()
                            .map(AssetPrice::getAsset)
                            .toList();

                    return persistenceContextExecutor.runInTransaction(() ->
                            assetRepository.upsertAssets(originalAssets)
                                    .flatMap(upsertedAssets -> {
                                        Map<Ticker, Asset> labeledAssets = upsertedAssets.stream().collect(Collectors.toMap(
                                                Asset::getTicker,
                                                asset -> asset
                                        ));

                                        List<AssetPrice> listWithUpsertedAssets = listWithOriginalAssets.stream().map(original -> {
                                                    Asset newAsset = labeledAssets.get(original.getAsset().getTicker());
                                                    return new AssetPrice(original, newAsset);
                                                })
                                                .toList();

                                        return assetRepository.saveAssetPrices(listWithUpsertedAssets);
                                    })
                    );
                });
    }
}
