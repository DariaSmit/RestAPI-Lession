package tests;

import com.codeborne.selenide.WebDriverRunner;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import owner.UserOwner;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.apache.hc.core5.http.message.MessageSupport.format;
import static org.hamcrest.Matchers.is;

public class DemoShopTestAuth extends TestBase {

    static UserOwner userConfig = ConfigFactory.create(UserOwner.class);
    static String userLogin = userConfig.userLogin();
    static String userPassword = userConfig.userPassword();
    static String authCookieName = "NOPCOMMERCE.AUTH";
    static RequestSpecification updateWish;

    @DisplayName("auth")
    @Test
    void auth() {
        String authCookieNameValue =
                given()
                        .filter(withCustomTemplates())
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("Email", userLogin)
                        .formParam("Password", userPassword)
                        .log().all()
                        .when()
                        .post("/login")
                        .then()
                        .log().all()
                        .statusCode(302)
                        .extract().cookie(authCookieName);
        step("Открыть легковесную страницу сайта", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Добавить куки в открытый браузер(с легковесной страницей)", () -> {
            Cookie authCookie = new Cookie(authCookieName, authCookieNameValue);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });
        step("Открыть главную страницу сайта", () ->
                open(""));
        step("Проверка успешной авторизации по логину в личном кабинете", () -> {
            $(".account").shouldHave(text(userLogin));
        });
    }

    @Test
    void Test2() {
        String authCookieNameValue1 =
                given()
                        .filter(withCustomTemplates())
                        .contentType("application/x-www-form-urlencoded")
                        .body(format("Email=%s&Password=%s&RememberMe=false", userLogin, userPassword))
                        .log().all()
                        .when()
                        .post("/login")
                        .then()
                        .log().all()
                        .statusCode(302)
                        .extract().cookie(authCookieName);


        String body = "product_attribute_72_5_18=53&product_attribute_72_6_19=55&product_attribute_72_3_20=57&addtocart_72.EnteredQuantity=1";
        String authBodyCookie= "NOPCOMMERCE.AUTH=EDA311E1575791EB8EC8CBDE60175E6246A552CF7440AA953C56A90BF019BE0617E77D1BC99FE31DAD1D64A351023EB2676CD7FB1533447A490D48F96B4D9ECAD5F7B46FD15AED82B695790B567F8D2DBC63BEAC4C773CF22B413E5A76351E52B8B0576376B516BA1F4632682AC7EB81945E57E67ED3AF482BEEC8CD333212096B711C7CB0A191763FA0EA7D2EDDD1BC";
        step("add prod in wishlist", () -> {
            String authCookieNameValue =

                    given()
                            .filter(withCustomTemplates())
                            .contentType("application/x-www-form-urlencoded")
                            .body(body)
                            .cookie(authBodyCookie)
                            .log().all()
                            .when()
                            .post("//addproducttocart/details/31/1")
                            .then()
                            .log().all()
                            .statusCode(200)
                            .body("success", is(true))
                            .extract().response().asPrettyString();
        });
        step("Открыть легковесную страницу сайта", () ->
        open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Добавить куки в открытый браузер(с легковесной страницей)", () -> {
            Cookie authCookie = new Cookie(authCookieName, authCookieNameValue1);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });
        step("open card", () -> {
            open("/cart");
        });
        step("check product", () -> {
            //$(".product-name").shouldHave(text("Simple computer"));
        });
    }

    @Test
    void authProduct() {
        String body = "product_attribute_72_5_18=53&product_attribute_72_6_19=55&product_attribute_72_3_20=57&addtocart_72.EnteredQuantity=1";
        String authBodyCookie= "NOPCOMMERCE.AUTH=EDA311E1575791EB8EC8CBDE60175E6246A552CF7440AA953C56A90BF019BE0617E77D1BC99FE31DAD1D64A351023EB2676CD7FB1533447A490D48F96B4D9ECAD5F7B46FD15AED82B695790B567F8D2DBC63BEAC4C773CF22B413E5A76351E52B8B0576376B516BA1F4632682AC7EB81945E57E67ED3AF482BEEC8CD333212096B711C7CB0A191763FA0EA7D2EDDD1BC";
        step("add prod in wishlist", () -> {
        String authCookieNameValue2 =

                given()
                        .filter(withCustomTemplates())
                        .contentType("application/x-www-form-urlencoded")
                        .body(body)
                        .cookie(authBodyCookie)
                        .log().all()
                        .when()
                        .post("//addproducttocart/details/31/1")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("success", is(true))
                        .extract().response().asPrettyString();

        step("Открыть легковесную страницу сайта", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Добавить куки в открытый браузер(с легковесной страницей)", () -> {
            Cookie authCookie = new Cookie(authCookieName, authCookieNameValue2);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });
        step("Открыть главную страницу сайта", () ->
                open(""));
        step("Проверка успешной авторизации по логину в личном кабинете", () -> {
            $(".account").shouldHave(text(userLogin));
        });
        });
    }
}


