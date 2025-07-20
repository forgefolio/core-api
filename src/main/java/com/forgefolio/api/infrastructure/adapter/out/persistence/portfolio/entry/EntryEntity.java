package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.entry;

import com.forgefolio.api.domain.model.portfolio.Entry;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "entries")
public class EntryEntity extends PanacheEntityBase {

    @Id
    private UUID id;

    @Column(name = "portfolio_id")
    private UUID portfolioId;

    @Column(name = "asset_id")
    private UUID assetId;

    private OffsetDateTime date;

    @Enumerated(EnumType.STRING)
    private Entry.Type type;

    private BigDecimal amount;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    public EntryEntity() {
    }

    public EntryEntity(Entry entry) {
        this.id = entry.getId().getValue();
        this.portfolioId = entry.getPortfolio().getId().getValue();
        this.assetId = entry.getAsset().getId().getValue();
        this.date = entry.getDate().toOffsetDateTime();
        this.type = entry.getType();
        this.amount = entry.getAmount().getValue();
        this.unitPrice = entry.getUnitPrice().getValue();
    }
}
