package com.forgefolio.api.application.service.asset;

import com.forgefolio.api.application.port.in.asset.FetchAssetPricesUseCase;
import com.forgefolio.api.application.port.out.asset.AssetPriceProvider;
import com.forgefolio.api.application.port.out.asset.AssetRepository;
import com.forgefolio.api.domain.model.asset.AssetPrice;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FetchAssetPricesService implements FetchAssetPricesUseCase {

    private final AssetPriceProvider assetPriceProvider;
    private final AssetRepository assetRepository;

    public FetchAssetPricesService(AssetPriceProvider assetPriceProvider, AssetRepository assetRepository) {
        this.assetPriceProvider = assetPriceProvider;
        this.assetRepository = assetRepository;
    }

    @Override
    public Uni<Void> fetchAssetPrices() {
        return assetPriceProvider.fetchAssetPrices()
                .flatMap(assetPrice ->
                        assetRepository.upsertAsset(assetPrice.getAsset())
                                .map(upsertedAsset -> new AssetPrice(assetPrice, upsertedAsset))
                                .flatMap(assetRepository::saveAssetPrice)
                );
    }
}
