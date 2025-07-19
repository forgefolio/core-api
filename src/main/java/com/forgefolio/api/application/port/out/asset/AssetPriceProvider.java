package com.forgefolio.api.application.port.out.asset;

import com.forgefolio.api.domain.model.asset.AssetPrice;
import io.smallrye.mutiny.Multi;

public interface AssetPriceProvider {

    Multi<AssetPrice> fetchAssetPrices();

}
