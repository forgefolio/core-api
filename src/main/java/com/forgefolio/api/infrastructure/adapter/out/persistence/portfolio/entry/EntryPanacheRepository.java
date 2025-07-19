package com.forgefolio.api.infrastructure.adapter.out.persistence.portfolio.entry;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class EntryPanacheRepository implements PanacheRepositoryBase<EntryEntity, UUID> {
}
