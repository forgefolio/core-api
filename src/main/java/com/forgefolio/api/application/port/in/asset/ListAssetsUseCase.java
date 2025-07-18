package com.forgefolio.api.application.port.in.asset;

import com.forgefolio.api.application.port.in.asset.response.AssetResponse;
import com.forgefolio.api.domain.pagination.PageResponse;
import com.forgefolio.api.domain.pagination.asset.ListAssetsCommand;
import io.smallrye.mutiny.Uni;

public interface ListAssetsUseCase {

    Uni<PageResponse<AssetResponse>> getAssets(ListAssetsCommand command);

}
