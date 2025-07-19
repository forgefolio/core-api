package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.goal;

import com.forgefolio.api.application.port.out.portfolio.GoalRepository;
import com.forgefolio.api.domain.model.portfolio.Goal;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GoalRepositoryAdapter implements GoalRepository {

    private final GoalPanacheRepository repository;

    public GoalRepositoryAdapter(GoalPanacheRepository repository) {
        this.repository = repository;
    }

    @Override
    public Uni<Void> save(Goal goal) {
        GoalEntity entity = new GoalEntity(goal);

        return repository.persist(entity).replaceWithVoid();
    }
}
