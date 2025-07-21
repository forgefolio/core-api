package com.forgefolio.api.infrastructure.adapter.in.rest.user;

import com.forgefolio.api.infrastructure.adapter.out.persistence.IntegrationTestcontainersManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(IntegrationTestcontainersManager.class)
class UserResourceTest {

    @Test
    @DisplayName("When creating a user, should return 200 with user ID")
    void createUser() {
        given()
                .when()
                .post("/users")
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }
}