package com.forgefolio.api.infrastructure.adapter.out.persistence.user;

import com.forgefolio.api.application.port.out.persistence.PersistenceContextExecutor;
import com.forgefolio.api.domain.exception.ErrorCode;
import com.forgefolio.api.domain.exception.NotFoundException;
import com.forgefolio.api.domain.model.shared.Id;
import com.forgefolio.api.domain.model.user.User;
import com.forgefolio.api.infrastructure.adapter.out.persistence.IntegrationTestcontainersManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


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
    @DisplayName("When saving a user, should persist the user and be able to find it by ID")
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
    @DisplayName("When using a valid user ID, should find the user by ID")
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

    @Test
    @DisplayName("When using an invalid user ID, should throw an NotFoundException with ErrorCode.USER_NOT_FOUND")
    @RunOnVertxContext
    void findByIdNotFound(UniAsserter asserter) {
        Id invalidId = new Id(UUID.randomUUID());

        asserter.assertFailedWith(
                () -> persistenceContextExecutor.runInSession(
                        () -> userRepositoryAdapter.findById(invalidId)
                ),
                (e) -> {
                    assertNotNull(e, "Exception should not be null");
                    assertInstanceOf(NotFoundException.class, e, "Should throw NotFoundException");

                    NotFoundException notFoundException = (NotFoundException) e;

                    assertEquals(ErrorCode.USER_NOT_FOUND, notFoundException.getErrorCode(), "Error code should be ErrorCode.USER_NOT_FOUND");
                }
        );
    }
}