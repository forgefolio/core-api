package com.forgefolio.api.infrastructure.adapter.out.persistence;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class IntegrationTestcontainersManager implements QuarkusTestResourceLifecycleManager {

    private PostgreSQLContainer<?> postgreSQLContainer;

    @Override
    public Map<String, String> start() {

        postgreSQLContainer = new PostgreSQLContainer<>("postgres:17")
                .withDatabaseName("forgefolio")
                .withUsername("forgefolio")
                .withPassword("forgefolio");

        postgreSQLContainer.start();

        String url = String.format(
                "postgresql://localhost:%d/forgefolio",
                postgreSQLContainer.getMappedPort(5432)
        );

        return Map.of(
                "quarkus.datasource.db-kind", "postgresql",
                "quarkus.datasource.reactive.url", url,
                "quarkus.datasource.username", "forgefolio",
                "quarkus.datasource.password", "forgefolio",
                "quarkus.hibernate-orm.database.generation", "drop-and-create"
        );
    }

    @Override
    public void stop() {
        if (postgreSQLContainer != null)
            postgreSQLContainer.stop();
    }
}
