package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.entry;

import com.forgefolio.api.application.port.out.persistence.portfolio.EntryRepository;
import com.forgefolio.api.domain.model.portfolio.Entry;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntryRepositoryAdapter implements EntryRepository {

    private final EntryPanacheRepository repository;

    public EntryRepositoryAdapter(EntryPanacheRepository repository) {
        this.repository = repository;
    }

    @Override
    public Uni<Void> save(Entry entry) {
        EntryEntity entity = new EntryEntity(entry);

        return repository.persist(entity).replaceWithVoid();
    }
}
