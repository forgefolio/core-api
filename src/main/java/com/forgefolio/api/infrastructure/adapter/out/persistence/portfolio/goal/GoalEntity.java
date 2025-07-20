package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.goal;

import com.forgefolio.api.domain.model.portfolio.Goal;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "goals")
public class GoalEntity extends PanacheEntityBase {

    @Id
    private UUID id;

    @Column(name = "portfolio_id")
    private UUID portfolioId;

    @Column(name = "asset_id")
    private UUID assetId;

    @Enumerated(EnumType.STRING)
    private Goal.Type type;

    private BigDecimal amount;
    private BigDecimal percentage;
    private BigDecimal value;

    public GoalEntity() {
    }

    public GoalEntity(Goal goal) {
        this.id = goal.getId().getValue();
        this.portfolioId = goal.getPortfolio().getId().getValue();
        this.assetId = goal.getAsset().getId().getValue();
        this.type = goal.getType();
        this.amount = goal.getAmount() != null ? goal.getAmount().getValue() : null;
        this.percentage = goal.getPercentage() != null ? goal.getPercentage().getValue() : null;
        this.value = goal.getValue() != null ? goal.getValue().getValue() : null;
    }
}
