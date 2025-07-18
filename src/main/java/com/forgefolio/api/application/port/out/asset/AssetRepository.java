package com.forgefolio.api.application.port.out.asset;

import com.forgefolio.api.domain.dto.Pair;
import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.asset.AssetPrice;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.pagination.PageResponse;
import com.forgefolio.api.domain.pagination.asset.ListAssetsCommand;
import io.smallrye.mutiny.Uni;

public interface AssetRepository {

    Uni<PageResponse<Pair<Asset, AssetPrice>>> findAssetsWithCurrentPrices(ListAssetsCommand command);

    Uni<Asset> findById(Id assetId);

    Uni<Asset> upsertAsset(Asset asset);

    Uni<Void> saveAssetPrice(AssetPrice assetPrice);
}
