package com.forgefolio.api.infrastructure.adapter.in.rest.portfolio;

import com.forgefolio.api.infrastructure.adapter.out.persistence.IntegrationTestcontainersManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(IntegrationTestcontainersManager.class)
class PortfolioResourceTest {

    @Test
    @DisplayName("When the user id is not and UUID, should return 400")
    void createPortfolioWithInvalidUserId() {
        String body = """
                {
                    "userId": "invalid-uuid",
                    "name": "This is a test portfolio"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/portfolios")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("When the user is not found, should return 404")
    void createPortfolioWithNotFoundUserId() {
        String body = """
                {
                    "userId": "82fd8238-2b9d-4734-b566-655bda454434",
                    "name": "This is a test portfolio"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/portfolios")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("When the user id is valid, should return 200 with portfolio details")
    void createPortfolioWithValidUserId() {
        Response createUserResponse = given()
                .when()
                .post("/users")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .extract()
                .response();

        String userId = createUserResponse.path("id");
        String portfolioName = "This is a test portfolio";

        String body = """
                {
                    "userId": "%s",
                    "name": "%s"
                }
                """.formatted(userId, portfolioName);

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/portfolios")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("userId", notNullValue())
                .body("name", notNullValue())
                .body("userId", equalTo(userId))
                .body("name", equalTo(portfolioName));
    }
}