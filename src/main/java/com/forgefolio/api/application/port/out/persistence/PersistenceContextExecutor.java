package com.forgefolio.api.application.port.out.persistence;

import io.smallrye.mutiny.Uni;

import java.util.function.Supplier;

public interface PersistenceContextExecutor {
    <T> Uni<T> runInTransaction(Supplier<Uni<T>> action);

    <T> Uni<T> runInSession(Supplier<Uni<T>> action);
}
