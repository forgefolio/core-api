package com.forgefolio.api.application.port.out.portfolio;

import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.portfolio.Portfolio;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.shared.Quantity;
import io.smallrye.mutiny.Uni;

public interface PortfolioRepository {

    Uni<Void> save(Portfolio portfolio);

    Uni<Portfolio> findById(Id id);

    Uni<Void> increaseAssetAmount(Portfolio portfolio, Asset asset, Quantity quantity);

    Uni<Void> decreaseAssetAmount(Portfolio portfolio, Asset asset, Quantity quantity);
}
