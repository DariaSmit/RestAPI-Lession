package testB;

import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class testB extends TestBaseB {

    @Test
    @DisplayName("reg")
    void Reg() {
        Faker faker = new Faker();
        String name = faker.name().fullName(),
                email = faker.internet().emailAddress(),
                password = "1234",
                nameCookieUser = "misterti",
        task = faker.animal().name(),
                taskDescription = faker.animal().name();
        step("reg", () -> {
            given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Name", name)
                    .formParam("Email", email)
                    .formParam("Password", password)
                    .formParam("act_register_now", "Зарегистрироваться")
                    .log().all()
                    .when()
                    .post("/login/index.html")
                    .then()
                    .log().all()
                    .statusCode(200);
        });
        step("cooklie ", ()->{
            String authCookieValue = given()
                    .filter(withCustomTemplates())
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("Login", email)
                    .formParam("Password", password)
                    .log().all()
                    .when()
                    .post("/user/login/index.html")
                    .then()
                    .log().all()
                    .statusCode(302)
                    .extract().cookie(nameCookieUser);

        step("Поставить задачу",()->{  given()
                .filter(withCustomTemplates())
                .cookie(nameCookieUser, authCookieValue)
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", task)
                .formParam("description", taskDescription)
                .formParam("user", "1234")
                .log().all()
                .when()
                .post("/tasks/do")
                .then().log().all()
                .statusCode(302);});


        step("Открыть легковесную страницу сайта", () ->
                open("/tmp/default_avatar.jpg"));

        step("Добавить куки в открытый браузер(с легковесной страницей)", () -> {
            //задаю название кук и значение
            Cookie authCookie = new Cookie(nameCookieUser, authCookieValue);
            // через импорт import org.openqa.selenium.Cookie;
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });
        });
    step("Открыть страницу 'Задания', в личном кабинете авторизованного пользователя", () ->
    open("/tasks/index.html"));
    step("Проверка успешного добавляения задачи", () -> {
        $(".table").shouldHave(text(task));
    });

}

}
