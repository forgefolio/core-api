package com.forgefolio.api.infrastructure.adapter.out.persistence;

import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.function.Supplier;

@ApplicationScoped
public class PersistenceContextExecutorAdapter implements PersistenceContextExecutor {

    @Override
    @WithTransaction
    public <T> Uni<T> runInTransaction(Supplier<Uni<T>> action) {
        return action.get();
    }

    @Override
    @WithSession
    public <T> Uni<T> runInSession(Supplier<Uni<T>> action) {
        return action.get();
    }
}
