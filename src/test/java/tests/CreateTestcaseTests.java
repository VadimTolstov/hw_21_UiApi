package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.CreateTestCaseBody;
import models.CreateTestCaseResponse;
import models.StepBodyModel;
import models.StepTestCaseResponse;
import models.data.ApiData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
            projectId = "2237",
            leafId = "18046";
    String xxsrfToken = "8806e9d1-c1ef-4acf-860e-c9078087d0f1";
    String allureToken = "ed2bbde7-fa05-44c2-8b17-7c91d8e2cfcb";

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://allure.autotests.cloud";
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";

        RestAssured.baseURI = "https://allure.autotests.cloud";
    }

    @Test
    @DisplayName("Создание тест кейса")
    void createWitApiUIExtendedTest() {
        Faker faker = new Faker();
        String testCaseName = faker.name().fullName();


        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponse createTestCaseResponse = step("Create testcase", () ->
                given()
                        .log().all()
                        .header("X-XSRF-TOKEN", xxsrfToken)
                        .cookies("XSRF-TOKEN", xxsrfToken,
                                "ALLURE_TESTOPS_SESSION", allureToken)
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .queryParam("projectId", projectId)
                        .when()
                        .post("/api/rs/testcasetree/leaf")
                        .then()
                        .log().status()
                        .log().body()
                        .log().all()
                        .statusCode(200)
                        .body("statusName", is("Draft"))
                        .body("name", is(testCaseName))
                        .extract().as(CreateTestCaseResponse.class));

        step("Verify testcase name", () -> {
            open("/favicon.ico");

            Cookie authoriztionCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureToken);
            getWebDriver().manage().addCookie(authoriztionCookie);

            Integer testCesaId = createTestCaseResponse.getId();
            String testCaseUrl = format("/project/%s/test-cases/%s?", projectId, testCesaId);
            open(testCaseUrl);

            $(".TestCaseLayout__name").shouldHave(text(testCaseName));
        });
    }

    @Test
    @DisplayName("Изменение имяни тест кейса")
    void editingTest() {
        Faker faker = new Faker();
        String testCaseName = faker.name().fullName();

        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponse createTestCaseResponse = step("Create testcase", () ->
                given()
                        .log().all()
                        .header("X-XSRF-TOKEN", xxsrfToken)
                        .cookies("XSRF-TOKEN", xxsrfToken,
                                "ALLURE_TESTOPS_SESSION", allureToken)
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .queryParam("projectId", projectId)
                        .queryParam("leafId", leafId)
                        .when()
                        .post("/api/rs/testcasetree/leaf/rename")
                        .then()
                        .log().status()
                        .log().body()
                        .log().all()
                        .statusCode(200)
                        .body("statusName", is("Draft"))
                        .body("name", is(testCaseName))
                        .extract().as(CreateTestCaseResponse.class));

        step("Verify testcase name", () -> {
            open("/favicon.ico");

//            Cookie authoriztionCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureToken);
            getWebDriver().manage().addCookie(new Cookie("ALLURE_TESTOPS_SESSION", allureToken));

            Integer testCesaId = createTestCaseResponse.getId();
            String testCaseUrl = format("/project/%s/test-cases/%s?", projectId, testCesaId);
            open(testCaseUrl);
            $(".TestCaseLayout__name").shouldHave(text(testCaseName));
        });
    }

    @Test
    @DisplayName("Добавление шагов тест кейса")
    void stepTest() {

        StepBodyModel stepBodyModel = new StepBodyModel();

        StepTestCaseResponse stepTestCaseResponse = step("Create testcase", () ->
                given()
                        .log().all()
                        .header("X-XSRF-TOKEN", xxsrfToken)
                        .cookies("XSRF-TOKEN", xxsrfToken,
                                "ALLURE_TESTOPS_SESSION", allureToken)
                        .contentType(ContentType.JSON)
                        .body(stepBodyModel)
//                        .queryParam("projectId", projectId)
//                        .queryParam("leafId", leafId)
                        .when()
                        .post("/api/rs/testcase/18460/scenario")
                        .then()
                        .log().status()
                        .log().body()
                        .log().all()
                        .statusCode(200)
//                        .body("statusName", is("Draft"))
//                        .body("name", is(testCaseName))
                        .extract().as(StepTestCaseResponse.class));

    }

//    curl 'https://allure.autotests.cloud/api/rs/testcase/18025/scenario' \
//            -H 'Accept: application/json, text/plain, */*' \
//            -H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' \
//            -H 'Connection: keep-alive' \
//            -H 'Content-Type: application/json;charset=UTF-8' \
//            -H 'Cookie: XSRF-TOKEN=1a864e3a-59c3-41f5-9b32-2e26fd7fdba4; ALLURE_TESTOPS_SESSION=03a4c0d0-99c0-4fc8-8b18-e8344d8c5548' \
//            -H 'Origin: https://allure.autotests.cloud' \
//            -H 'Sec-Fetch-Dest: empty' \
//            -H 'Sec-Fetch-Mode: cors' \
//            -H 'Sec-Fetch-Site: same-origin' \
//            -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36' \
//            -H 'X-XSRF-TOKEN: 1a864e3a-59c3-41f5-9b32-2e26fd7fdba4' \
//            -H 'sec-ch-ua: "Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"' \
//            -H 'sec-ch-ua-mobile: ?0' \
//            -H 'sec-ch-ua-platform: "Windows"' \
//            --data-raw '{"steps":[{"name":"12231","spacing":""},{"name":"123","spacing":""}],"workPath":[1]}' \
//            --compressed
    @Test
    @Disabled
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

    @Test
    @Disabled
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

        Date expDate = new Date();
        expDate.setTime(expDate.getTime() + (10000 * 10000));
        Cookie cookie = new Cookie("ALLURE_TESTOPS_SESSION", sessionId, "allure.autotests.cloud", "/", expDate);
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
        Selenide.refresh();

    }

}