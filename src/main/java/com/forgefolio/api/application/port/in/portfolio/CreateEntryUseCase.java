package com.forgefolio.api.application.port.in.portfolio;

import com.forgefolio.api.application.port.in.portfolio.command.CreateEntryCommand;
import com.forgefolio.api.application.port.in.portfolio.response.EntryResponse;
import io.smallrye.mutiny.Uni;

public interface CreateEntryUseCase {

    Uni<EntryResponse> createEntry(CreateEntryCommand command);

}
