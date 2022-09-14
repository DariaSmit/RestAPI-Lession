package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenoidTests {


    @Test
    void checkTotal() {
        given()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }

    @Test
    void checkTotal2() {
        given()
                .log().all()
                .when()
                .get("https://selenoid.autotests.cloud/status")
                .then()
                .body("total", is(20));
    }

    @Test
    void checkTotal3() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .body("browser.chrome", hasKey("100.0"));
    }

    @Test
    void checkTotal33bad() {
        Response response = get("https://selenoid.autotests.cloud/status")
                .then()
                .extract().response();
        System.out.println(response.asString());
        String expectedResponse = "{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0  ,\"browsers\":" +
                "{\"android\":{\"8.1\":{}},\"chrome\":{\"100.0\":{},\"99.0\":{}},\"chrome-mobile\":" +
                "{\"86.0\":{}},\"firefox\":{\"97.0\":{},\"98.0\":{}},\"opera\":{\"84.0\":{},\"85.0\":{}}}}\n";

        assertEquals(expectedResponse, response.asString());
    }

    @Test
    void checkTotal33Good() {
        Integer total = get("https://selenoid.autotests.cloud/status")
                .then()
                .extract().path("total");
        System.out.println(total);
        Integer expectedTotal = 20;

        assertEquals(expectedTotal, total);
    }

    @Test
    void checkTotalCode() {
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200);
    }

    @Test
    void checkTotalCodeAu() {
        get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(401);
    }

    @Test
    void checkTotalCodeAutTotal() {
        given()
                .auth().basic("user1", "1234")
        .get("https://selenoid.autotests.cloud/wd/hub/status")
                .then()
                .statusCode(200);
    }
}
