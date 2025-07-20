package com.forgefolio.api.infrastructure.adapter.out.api.asset;

import com.forgefolio.api.application.port.out.api.asset.AssetPriceProvider;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.asset.AssetPrice;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.ZonedDateTime;
import java.util.List;

@ApplicationScoped
public class AssetPriceProviderAdapter implements AssetPriceProvider {

    private final AssetPriceRestClient client;
    private final AssetPriceProviderProperties properties;

    public AssetPriceProviderAdapter(
            @RestClient
            AssetPriceRestClient client,
            AssetPriceProviderProperties properties
    ) {
        this.client = client;
        this.properties = properties;
    }

    @Override
    public Uni<List<AssetPrice>> fetchAssetPrices() {
        ZonedDateTime now = ZonedDateTime.now();

        return client.getAssetList(1, 1000, "stock", properties.getToken())
                .onItem().ifNull().failWith(new RuntimeException("Failed to fetch asset prices"))
                .map(dto -> dto.stocks().stream()
                        .map(stock -> {
                            Asset asset = new Asset(stock.stock(), stock.name());
                            return new AssetPrice(asset, now, stock.close());
                        })
                        .toList());
    }
}
