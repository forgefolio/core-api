package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.goal;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class GoalPanacheRepository implements PanacheRepositoryBase<GoalEntity, UUID> {
}
