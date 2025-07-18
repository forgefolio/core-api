package com.forgefolio.api.infrastructure.adapter.in.rest.portfolio;

import com.forgefolio.api.application.port.in.portfolio.CreateEntryUseCase;
import com.forgefolio.api.application.port.in.portfolio.command.CreateEntryCommand;
import com.forgefolio.api.application.port.in.portfolio.response.EntryResponse;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.ReactiveRoutes;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RouteBase(path = "entries", produces = ReactiveRoutes.APPLICATION_JSON)
public class EntryController {

    private final CreateEntryUseCase createEntryUseCase;

    public EntryController(CreateEntryUseCase createEntryUseCase) {
        this.createEntryUseCase = createEntryUseCase;
    }

    @Route(methods = Route.HttpMethod.POST)
    public Uni<EntryResponse> save(
            RoutingContext ctx,
            @Body CreateEntryCommand command
    ) {
        return createEntryUseCase.createEntry(command)
                .onItem().invoke(entryResponse -> {
                    String location = "/entries/" + entryResponse.getId();

                    ctx.response()
                            .setStatusCode(201)
                            .putHeader("Location", location)
                            .end(Json.encode(entryResponse));
                });
    }
}