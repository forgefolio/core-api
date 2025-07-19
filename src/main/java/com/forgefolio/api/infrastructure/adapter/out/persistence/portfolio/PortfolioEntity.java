package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio;

import com.forgefolio.api.domain.model.portfolio.Portfolio;
import com.forgefolio.api.domain.model.portfolio.PortfolioName;
import com.forgefolio.api.domain.model.user.User;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "portfolios",
        indexes = {
                @Index(name = "idx_portfolios_user_id", columnList = "userId")
        }
)
public class PortfolioEntity extends PanacheEntityBase {

    @Id
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    private String name;

    public PortfolioEntity() {
    }

    public PortfolioEntity(Portfolio portfolio) {
        this.id = portfolio.getId().getValue();
        this.userId = portfolio.getUser().getId().getValue();
        this.name = portfolio.getName().getValue();
    }

    public Portfolio toDomain() {
        return new Portfolio(
                new com.forgefolio.api.domain.model.shared.Id(this.id),
                new User(new com.forgefolio.api.domain.model.shared.Id(this.userId)),
                new PortfolioName(this.name)
        );
    }
}
