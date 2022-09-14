package tests;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.google.common.collect.Multisets.filter;
import static helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static tests.DemoShopTestAuth.*;

public class zz extends TestBase {

    @Test
    void authProduct() {
        String authCookieName = "NOPCOMMERCE.AUTH";
        String body = "product_attribute_72_5_18=53&product_attribute_72_6_19=55&product_attribute_72_3_20=57&addtocart_72.EnteredQuantity=1";
        String authCookieValue =
                "EDA311E1575791EB8EC8CBDE60175E6246A552CF7440AA953C56A90BF019BE0617E77D1BC99FE31DAD1D64A351023EB2676CD7FB1533447A490D48F96B4D9ECAD5F7B46FD15AED82B695790B567F8D2DBC63BEAC4C773CF22B413E5A76351E52B8B0576376B516BA1F4632682AC7EB81945E57E67ED3AF482BEEC8CD333212096B711C7CB0A191763FA0EA7D2EDDD1BC";

            String cartSize =

                    given()
                            .filter(withCustomTemplates())
                            .contentType("application/x-www-form-urlencoded")
                            .body(body)
                            .cookie(authCookieName, authCookieValue)
                            .log().all()
                            .when()
                            .post("/addproducttocart/details/31/1")
                            .then()
                            .log().all()
                            .statusCode(200)
                            .body("success", is(true))
                            .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                            .body("updatetopcartsectionhtml", notNullValue())
                            .extract()
                            .path("updatetopcartsectionhtml");


            step("Открыть легковесную страницу сайта", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));

            step("Добавить куки в открытый браузер(с легковесной страницей)", () -> {
                Cookie authCookie = new Cookie(authCookieName, authCookieValue);
                WebDriverRunner.getWebDriver().manage().addCookie(authCookie);

        });
            step("open main",()->
                    open(""));
    step("verify shopcart", ()->
        $("#topcartlink .cart-qty").shouldHave(text(cartSize))
    );
    }


    @Test
    void authProductDinamic() {


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

        String authCookieName = "NOPCOMMERCE.AUTH";
        String body = "product_attribute_72_5_18=53&product_attribute_72_6_19=55&product_attribute_72_3_20=57&addtocart_72.EnteredQuantity=1";
        String authCookieValue =
                "EDA311E1575791EB8EC8CBDE60175E6246A552CF7440AA953C56A90BF019BE0617E77D1BC99FE31DAD1D64A351023EB2676CD7FB1533447A490D48F96B4D9ECAD5F7B46FD15AED82B695790B567F8D2DBC63BEAC4C773CF22B413E5A76351E52B8B0576376B516BA1F4632682AC7EB81945E57E67ED3AF482BEEC8CD333212096B711C7CB0A191763FA0EA7D2EDDD1BC";

        String cartSize =
                given()
                        .filter(withCustomTemplates())
                        .contentType("application/x-www-form-urlencoded")
                        .body(body)
                        .cookie(authCookieName, authCookieValue)
                        .log().all()
                        .when()
                        .post("/addproducttocart/details/31/1")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("success", is(true))
                        .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                        .body("updatetopcartsectionhtml", notNullValue())
                        .extract()
                        .path("updatetopcartsectionhtml");


        step("Открыть легковесную страницу сайта", () ->
                open("/Themes/DefaultClean/Content/images/logo.png"));

        step("Добавить куки в открытый браузер(с легковесной страницей)", () -> {
            Cookie authCookie = new Cookie(authCookieName, authCookieValue);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);

        });
        step("open main",()->
                open(""));
        step("verify shopcart", ()->
                $("#topcartlink .cart-qty").shouldHave(text(cartSize))
        );
    }
    @Test
    void authProductNewUser() {

        String body = "product_attribute_72_5_18=53&product_attribute_72_6_19=55&product_attribute_72_3_20=57&addtocart_72.EnteredQuantity=1";

                given()
                        .filter(withCustomTemplates())
                        .contentType("application/x-www-form-urlencoded")
                        .body(body)
                        .log().all()
                        .when()
                        .post("/addproducttocart/details/31/1")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .body("success", is(true))
                        .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                        .body("updateflyoutcartsectionhtml", notNullValue())
                        .body("updatetopcartsectionhtmll", is("(1)"));

    }
}
