package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.CreateTestCaseBody;
import models.CreateTestCaseResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Date;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;

public class CreateTestcaseTests {

    static String login = "allure8",
            password = "allure8",
            projectId = "2237";

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://allure.autotests.cloud";
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";

        RestAssured.baseURI = "https://allure.autotests.cloud";
    }


    @Test
    void createWitApiUIExtendedTest33() {

        Selenide.open("https://allure.autotests.cloud/login");
        String xsrfTofen = WebDriverRunner.getWebDriver().manage().getCookieNamed("XSRF-TOKEN").getValue();


        String sessionId = given()
                .contentType(ContentType.MULTIPART)
                .cookie("XSRF-TOKEN", xsrfTofen)
                .multiPart("username", "allure8")
                .multiPart("password", " allure8")
                .log().all()
                .post("/api/login/system")
                .then().log().all().extract().cookie("ALLURE_TESTOPS_SESSION");

        Date expDate = new Date();
        expDate.setTime(expDate.getTime() + (10000 * 10000));
        Cookie cookie = new Cookie("ALLURE_TESTOPS_SESSION", sessionId, "allure.autotests.cloud", "/", expDate);
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
        Selenide.refresh();

    }

//    curl 'https://allure.autotests.cloud/api/login/system' \
//            -H 'Accept: application/json, text/plain, */*' \
//            -H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' \
//            -H 'Cache-Control: no-cache' \
//            -H 'Connection: keep-alive' \
//            -H 'Content-Type: application/x-www-form-urlencoded' \
//            -H 'Cookie: REDIRECT_URI=L3Byb2plY3QvMjIzNy90ZXN0LWNhc2VzLzE3ODg0P3NlYXJjaD1XM3NpYVdRaU9pSnpkR0YwZFhNaUxDSjBlWEJsSWpvaWJHOXVaMEZ5Y21GNUlpd2lkbUZzZFdVaU9sc2lMVEVpWFgxZCZ0cmVlSWQ9MA==; XSRF-TOKEN=01391561-7eea-4e9b-9c11-4f333247b0f3' \
//            -H 'Origin: https://allure.autotests.cloud' \
//            -H 'Pragma: no-cache' \
//            -H 'Sec-Fetch-Dest: empty' \
//            -H 'Sec-Fetch-Mode: cors' \
//            -H 'Sec-Fetch-Site: same-origin' \
//            -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36' \
//            -H 'X-XSRF-TOKEN: 01391561-7eea-4e9b-9c11-4f333247b0f3' \
//            -H 'sec-ch-ua: "Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"' \
//            -H 'sec-ch-ua-mobile: ?0' \
//            -H 'sec-ch-ua-platform: "Windows"' \
//            --data-raw 'username=allure8&password=allure8' \
//            --compressed

    @Test
    void createWitApiUIExtendedTest32() {

        Selenide.open("https://allure.autotests.cloud/login");
        String xsrfTofen = WebDriverRunner.getWebDriver().manage().getCookieNamed("XSRF-TOKEN").getValue();

        String sessionId = given()
                .cookie("XSRF-TOKEN", xsrfTofen)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
//                .cookie("XSRF-TOKEN", xsrfTofen)
                .formParam("username", "allure8")
                .formParam("password", "allure8")
                .log().all()
                .when()
                .post("/api/login/system")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .cookie("ALLURE_TESTOPS_SESSION");

        WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("ALLURE_TESTOPS_SESSION", sessionId));
        Selenide.refresh();
    }
}