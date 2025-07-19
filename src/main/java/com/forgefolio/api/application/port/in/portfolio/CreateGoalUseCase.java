package com.forgefolio.api.application.port.in.portfolio;

import com.forgefolio.api.application.port.in.portfolio.command.CreateGoalCommand;
import com.forgefolio.api.application.port.in.portfolio.response.GoalResponse;
import io.smallrye.mutiny.Uni;

public interface CreateGoalUseCase {

    Uni<GoalResponse> createGoal(CreateGoalCommand command);

}
