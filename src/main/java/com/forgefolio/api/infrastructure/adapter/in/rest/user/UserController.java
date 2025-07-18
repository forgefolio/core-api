package com.forgefolio.api.infrastructure.adapter.in.rest.user;

import com.forgefolio.api.application.port.in.user.CreateUserUseCase;
import com.forgefolio.api.application.port.in.user.response.UserResponse;
import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RouteBase(path = "users", produces = ReactiveRoutes.APPLICATION_JSON)
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @Route(methods = Route.HttpMethod.POST)
    public Uni<UserResponse> createUser(
            RoutingContext ctx
    ) {
        return createUserUseCase.createUser()
                .onItem().invoke(userResponse -> {
                    String location = "/users/" + userResponse.getId();

                    ctx.response()
                            .setStatusCode(201)
                            .putHeader("Location", location)
                            .end(Json.encode(userResponse));
                });
    }

}
