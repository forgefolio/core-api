package com.forgefolio.api.application.port.out.asset;

import com.forgefolio.api.domain.model.asset.AssetPrice;
import io.smallrye.mutiny.Uni;

public interface AssetPriceProvider {

    Uni<AssetPrice> fetchAssetPrices();

}
