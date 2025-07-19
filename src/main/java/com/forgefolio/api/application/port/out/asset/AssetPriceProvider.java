package com.forgefolio.api.application.port.out.asset;

import com.forgefolio.api.domain.model.asset.AssetPrice;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface AssetPriceProvider {

    Uni<List<AssetPrice>> fetchAssetPrices();

}
