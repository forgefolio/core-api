package com.forgefolio.api.infrastructure.adapter.in.rest.user;

import com.forgefolio.api.application.port.in.user.CreateUserUseCase;
import com.forgefolio.api.application.port.in.user.response.UserResponse;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserResource {

    private final CreateUserUseCase createUserUseCase;

    public UserResource(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @Route(methods = Route.HttpMethod.POST, path = "/users")
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
