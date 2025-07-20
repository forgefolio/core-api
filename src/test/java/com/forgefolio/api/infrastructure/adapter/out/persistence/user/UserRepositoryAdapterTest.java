package com.forgefolio.api.infrastructure.adapter.out.persistence.user;

import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.domain.model.user.User;
import com.forgefolio.api.infrastructure.adapter.out.persistence.IntegrationTestcontainersManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@QuarkusTest
@QuarkusTestResource(IntegrationTestcontainersManager.class)
class UserRepositoryAdapterTest {

    private final UserRepositoryAdapter userRepositoryAdapter;
    private final PersistenceContextExecutor persistenceContextExecutor;

    public UserRepositoryAdapterTest(UserRepositoryAdapter userRepositoryAdapter, PersistenceContextExecutor persistenceContextExecutor) {
        this.userRepositoryAdapter = userRepositoryAdapter;
        this.persistenceContextExecutor = persistenceContextExecutor;
    }

    @Test
    @RunOnVertxContext
    void save(UniAsserter asserter) {
        User user = new User();

        asserter.execute(() ->
                persistenceContextExecutor.runInTransaction(
                        () -> userRepositoryAdapter.save(user)
                )
        );

        asserter.assertThat(
                () -> persistenceContextExecutor.runInSession(
                        () -> userRepositoryAdapter.findById(user.getId())
                ),
                found -> {
                    assertNotNull(found, "User should not be null after saving");
                    assertEquals(user.getId(), found.getId(), "User ID should match after saving");
                }
        );
    }

    @Test
    @RunOnVertxContext
    void findById(UniAsserter asserter) {
        User user = new User();

        asserter.execute(() ->
                persistenceContextExecutor.runInTransaction(
                        () -> userRepositoryAdapter.save(user)
                )
        );

        asserter.assertThat(
                () -> persistenceContextExecutor.runInSession(
                        () -> userRepositoryAdapter.findById(user.getId())
                ),
                found -> {
                    assertNotNull(found, "User should not be null after saving");
                    assertEquals(user.getId(), found.getId(), "User ID should match after saving");
                }
        );
    }
}