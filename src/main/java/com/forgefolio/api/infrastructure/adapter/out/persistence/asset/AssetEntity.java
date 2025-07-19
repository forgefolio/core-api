package com.forgefolio.api.infrastructure.adapter.out.persistence.asset;

import com.forgefolio.api.domain.model.asset.Asset;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "assets",
        indexes = {
                @Index(name = "idx_assets_ticker", columnList = "ticker", unique = true)
        }
)
public class AssetEntity {

    @Id
    private UUID id;

    @Column(unique = true)
    private String ticker;

    private String name;

    public AssetEntity() {
    }

    public AssetEntity(Asset asset) {
        this.id = asset.getId().getValue();
        this.ticker = asset.getTicker().getValue();
        this.name = asset.getName();
    }

    public Asset toDomain() {
        return new Asset(id, ticker, name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
