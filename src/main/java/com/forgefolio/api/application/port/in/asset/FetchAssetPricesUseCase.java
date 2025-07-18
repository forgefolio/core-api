package com.forgefolio.api.application.port.in.asset;


import io.smallrye.mutiny.Uni;

public interface FetchAssetPricesUseCase {

    Uni<Void> fetchAssetPrices();

}
