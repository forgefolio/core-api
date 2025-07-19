package com.forgefolio.api.application.port.out.portfolio;

import com.forgefolio.api.domain.model.portfolio.Goal;
import io.smallrye.mutiny.Uni;

public interface GoalRepository {

    Uni<Void> save(Goal goal);

}
