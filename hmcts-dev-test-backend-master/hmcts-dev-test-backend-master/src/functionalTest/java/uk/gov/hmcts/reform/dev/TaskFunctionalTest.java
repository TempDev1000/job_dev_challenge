package uk.gov.hmcts.reform.dev;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskFunctionalTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void createTaskSuccess() {
        String requestBody = """
            {
              "title": "Functional Test Task",
              "description": "Testing task creation",
              "status": "TODO",
              "dueDate": "2025-12-10T15:30:00"
            }
            """;

        Response response = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/api/tasks")
            .then()
            .extract().response();

        assertEquals(201, response.statusCode(), "Expected HTTP status 201 for successful creation");
        assertTrue(response.asString().contains("Functional Test Task"), "Response should contain task title");
    }

    @Test
    public void createTaskValidationFail() {
        String requestBody = """
            {
              "title": "",
              "description": "Invalid task",
              "status": "TODO",
              "dueDate": "2025-12-10T15:30:00"
            }
            """;

        Response response = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
            .post("/api/tasks")
            .then()
            .extract().response();

        assertEquals(400, response.statusCode(), "Expected HTTP status 400 for validation error");
        assertTrue(response.asString().contains("Title is required"), "Response should contain validation message");
    }

    @Test
    public void getTasks() {
        Response response = given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/tasks")
            .then()
            .extract().response();

        assertEquals(200, response.statusCode(), "Expected HTTP status 200 for GET /tasks");
        // Optionally check that the response contains at least one task
        //assertTrue(response.asString().contains("Functional Test Task"), "Response should contain existing tasks");
    }
}
