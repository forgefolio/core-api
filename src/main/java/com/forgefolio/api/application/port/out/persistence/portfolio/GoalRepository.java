package com.forgefolio.api.application.port.out.persistence.portfolio;

import com.forgefolio.api.domain.model.portfolio.Goal;
import io.smallrye.mutiny.Uni;

public interface GoalRepository {

    Uni<Void> save(Goal goal);

}
