package com.forgefolio.api.infrastructure.adapter.out.persistence;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.flywaydb.core.Flyway;
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

        Integer mappedPort = postgreSQLContainer.getMappedPort(5432);
        String url = String.format(
                "postgresql://localhost:%d/forgefolio",
                mappedPort
        );

        Flyway flyway = Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:" + mappedPort + "/forgefolio", "forgefolio", "forgefolio")
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();

        return Map.of(
                "quarkus.datasource.jdbc", "false",
                "quarkus.datasource.db-kind", "postgresql",
                "quarkus.datasource.reactive.url", url,
                "quarkus.datasource.username", "forgefolio",
                "quarkus.datasource.password", "forgefolio",
                "quarkus.hibernate-orm.schema-management.strategy", "none"
        );
    }

    @Override
    public void stop() {
        if (postgreSQLContainer != null)
            postgreSQLContainer.stop();
    }
}
