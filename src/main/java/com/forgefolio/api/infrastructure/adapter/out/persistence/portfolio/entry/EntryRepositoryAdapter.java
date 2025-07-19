package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio;

import com.forgefolio.api.application.port.out.portfolio.EntryRepository;
import com.forgefolio.api.domain.model.portfolio.Entry;
import com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.entry.EntryEntity;
import com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.entry.EntryPanacheRepository;
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
