package com.forgefolio.api.application.port.out.portfolio;

import com.forgefolio.api.domain.model.portfolio.Entry;
import io.smallrye.mutiny.Uni;

public interface EntryRepository {

    Uni<Void> save(Entry entry);

}
