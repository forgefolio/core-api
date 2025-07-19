package com.forgefolio.api.infrastructure.adapter.out.persistence.asset;

import com.forgefolio.api.domain.model.asset.AssetPrice;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "asset_prices",
        indexes = {
                @Index(name = "idx_asset_prices_asset_id_date_desc", columnList = "assetId, date", unique = true)
        }
)
public class AssetPriceEntity {

    @Id
    private UUID id;
    private UUID assetId;
    private BigDecimal price;
    private OffsetDateTime date;

    public AssetPriceEntity() {
    }

    public AssetPriceEntity(AssetPrice price) {
        this.id = price.getId().getValue();
        this.assetId = price.getAsset().getId().getValue();
        this.price = price.getPrice().getValue();
        this.date = price.getDate().toOffsetDateTime();
    }
}
