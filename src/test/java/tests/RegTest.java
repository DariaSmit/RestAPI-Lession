package tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegTest {

    @Test
    void checkReqLogin() {
        String body = "{ \"email\": \"eve.holt@reqres.in\"," +
                "\"password\": \"cityslicka\" }";

        given()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void checkReqLoginMissingPass() {
        String body = "{ \"email\": \"eve.holt@reqres.in\"}";

        given()
                .log().body()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().body()
                .log().status()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
