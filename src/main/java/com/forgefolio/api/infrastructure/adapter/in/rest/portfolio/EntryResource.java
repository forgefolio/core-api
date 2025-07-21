package com.forgefolio.api.infrastructure.adapter.in.rest.portfolio;

import com.forgefolio.api.application.port.in.portfolio.CreateEntryUseCase;
import com.forgefolio.api.application.port.in.portfolio.command.CreateEntryCommand;
import com.forgefolio.api.application.port.in.portfolio.response.EntryResponse;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntryResource {

    private final CreateEntryUseCase createEntryUseCase;

    public EntryResource(CreateEntryUseCase createEntryUseCase) {
        this.createEntryUseCase = createEntryUseCase;
    }

    @Route(methods = Route.HttpMethod.POST, path = "/entries")
    public Uni<EntryResponse> save(
            @Body CreateEntryCommand command
    ) {
        return createEntryUseCase.createEntry(command);
    }
}