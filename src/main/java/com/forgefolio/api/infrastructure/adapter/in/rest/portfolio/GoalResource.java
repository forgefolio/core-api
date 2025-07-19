package com.forgefolio.api.infrastructure.adapter.in.rest.portfolio;

import com.forgefolio.api.application.port.in.portfolio.CreateGoalUseCase;
import com.forgefolio.api.application.port.in.portfolio.command.CreateGoalCommand;
import com.forgefolio.api.application.port.in.portfolio.response.GoalResponse;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GoalResource {

    private final CreateGoalUseCase createGoalUseCase;

    public GoalResource(CreateGoalUseCase createGoalUseCase) {
        this.createGoalUseCase = createGoalUseCase;
    }

    @Route(methods = Route.HttpMethod.POST, path = "/goals")
    public Uni<GoalResponse> createGoal(
            RoutingContext ctx,
            @Body CreateGoalCommand command
    ) {
        return createGoalUseCase.createGoal(command)
                .onItem().invoke(goalResponse -> {
                    String location = "/goals/" + goalResponse.getId();

                    ctx.response()
                            .setStatusCode(201)
                            .putHeader("Location", location)
                            .end(Json.encode(goalResponse));
                });
    }
}
