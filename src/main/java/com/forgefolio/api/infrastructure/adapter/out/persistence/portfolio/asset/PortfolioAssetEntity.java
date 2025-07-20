package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.asset;

import com.forgefolio.api.domain.model.asset.Asset;
import com.forgefolio.api.domain.model.portfolio.Portfolio;
import com.forgefolio.api.domain.model.shared.Quantity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "portfolio_assets")
@IdClass(PortfolioAssetEntity.ID.class)
public class PortfolioAssetEntity {

    @Id
    @Column(name = "portfolio_id")
    private UUID portfolioId;

    @Id
    @Column(name = "asset_id")
    private UUID assetId;

    private BigDecimal amount;

    public PortfolioAssetEntity() {
    }

    public PortfolioAssetEntity(Portfolio portfolio, Asset asset) {
        this.portfolioId = portfolio.getId().getValue();
        this.assetId = asset.getId().getValue();
        this.amount = BigDecimal.ZERO;
    }

    public static class ID implements Serializable {

        public UUID portfolioId;
        public UUID assetId;

        public ID() {
        }

        public ID(UUID portfolioId, UUID assetId) {
            this.portfolioId = portfolioId;
            this.assetId = assetId;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ID id)) return false;
            return Objects.equals(portfolioId, id.portfolioId) && Objects.equals(assetId, id.assetId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(portfolioId, assetId);
        }
    }

    public UUID getPortfolioId() {
        return portfolioId;
    }

    public UUID getAssetId() {
        return assetId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void decrementAmount(Quantity quantity) {
        this.amount = this.amount.subtract(quantity.getValue());
    }

    public void incrementAmount(Quantity quantity) {
        this.amount = this.amount.add(quantity.getValue());
    }
}
