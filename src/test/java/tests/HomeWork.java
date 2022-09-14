package tests;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class HomeWork {

    @Test
    void getTest1List () {

        given()
                .contentType(JSON)


                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .statusCode(200)
                .body("page", is(2))
                .body( "per_page", is(6))
                .body( "total",is(12))
                .body("total_pages",is(2));
    }

    @Test
    void CreateUser () {
        String dataUser = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().all()
                .body(dataUser)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }
    @Test
    void TestUpdateUser () {
        String dataUser = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        given()
                .body(dataUser)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void deleteUser () {
       given()
               .when()
               .delete("https://reqres.in/api/users/2")
               .then()
               .statusCode(204);
    }
    @Test
    @Disabled
    void  registerSuccesfull () {
        String regData = "{ \"email\": \"eve.holt@reqres.in\"," +
                           " \"password\": \"pistol\" }";
        given()

                .body(regData)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/registe")
                .then()
                .log().body()
                .statusCode(201)

                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
