package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Date;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.hamcrest.Matchers.is;

public class CreateTestcaseTests {

    private final Faker faker = new Faker();

    static String login = "allure8",
            password = "allure8",
            projectId = "2237",
            leafId = "18046";
    String xxsrfToken = "4009dad4-7721-4ee4-9e76-edc2b82a9128";
    String allureToken = "528cf7cf-4105-43af-b169-16d00fa1773f";

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
    @DisplayName("Обновление шагов тест-кейса")
    void updateTestCaseStepsTest() {

        // В идеале тут создать тест-кейс, который будет исключительно для этого теста

        // Создаем тестовые данные - нашу модель с шагами теста
        TestCaseScenarioDto scenarioDto = new TestCaseScenarioDto()
                .addStep(new TestCaseScenarioDto.Step("Step 1", "st-1"))
                .addStep(new TestCaseScenarioDto.Step("Step 2", "st-2"))
                .addStep(new TestCaseScenarioDto.Step("Step 3", "st-3"));


        TestCaseScenarioDto response = step("Update testcase steps", () ->
                given().log().all()
                        .header("X-XSRF-TOKEN", xxsrfToken)
                        .cookies("XSRF-TOKEN", xxsrfToken,
                                "ALLURE_TESTOPS_SESSION", allureToken)
                        .contentType(ContentType.JSON)
                        .body(scenarioDto)
                        .when()
                        .post("/api/rs/testcase/18460/scenario")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(TestCaseScenarioDto.class));

        // Теперь в переменной response содержится ответ сервера. Можем с ним что-нибудь сделать
        // Например, проверить, что кол-во созданных шагов = 3 (кол-ву, которое изначально добавили)
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
    @Disabled("Не работает с ContentType.MULTIPART")
    void Authorization1() {

        Selenide.open("https://allure.autotests.cloud/login");
        String xsrfTofen = WebDriverRunner.getWebDriver().manage().getCookieNamed("XSRF-TOKEN").getValue();


        String sessionId = given()
                .contentType(ContentType.MULTIPART)
                .header("X-XSRF-TOKEN", xsrfTofen)
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
//            -H 'Accept-Language: ru,en;q=0.9' \
//            -H 'Cache-Control: no-cache' \
//            -H 'Connection: keep-alive' \
//            -H 'Content-Type: application/x-www-form-urlencoded' \
//            -H 'Cookie: _cc_id=b1975b0072d5e90371807e01eb50f935; _ga_MVRXK93D28=GS1.1.1680503960.1.0.1680504022.0.0.0; _ga=GA1.1.930498322.1680503961; XSRF-TOKEN=4009dad4-7721-4ee4-9e76-edc2b82a9128; REDIRECT_URI=L3Byb2plY3QvMjIzNy90ZXN0LWNhc2VzLzE4NDYwP3RyZWVJZD0wP3RyZWVJZD0w' \
//            -H 'Origin: https://allure.autotests.cloud' \
//            -H 'Pragma: no-cache' \
//            -H 'Sec-Fetch-Dest: empty' \
//            -H 'Sec-Fetch-Mode: cors' \
//            -H 'Sec-Fetch-Site: same-origin' \
//            -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 YaBrowser/23.3.3.719 Yowser/2.5 Safari/537.36' \
//            -H 'X-XSRF-TOKEN: 4009dad4-7721-4ee4-9e76-edc2b82a9128' \
//            -H 'sec-ch-ua: "Chromium";v="110", "Not A(Brand";v="24", "YaBrowser";v="23"' \
//            -H 'sec-ch-ua-mobile: ?0' \
//            -H 'sec-ch-ua-platform: "Windows"' \
//            --data-raw 'username=allure8&password=allure8' \
//            --compressed

    Cookie cookie = new Cookie("ALLURE_TESTOPS_SESSION", "allure.autotests.cloud");

    @Test
    @DisplayName("Авторизация пользователя")
    void AuthorizationTest2() {

        Selenide.open("https://allure.autotests.cloud/login");
        String xsrfToken = WebDriverRunner.getWebDriver().manage().getCookieNamed("XSRF-TOKEN").getValue();

        String sessionId = given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .header("X-XSRF-TOKEN", xsrfToken)
                .cookie("XSRF-TOKEN", xsrfToken)
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
        cookie = new Cookie("ALLURE_TESTOPS_SESSION", sessionId, "allure.autotests.cloud", "/", expDate);
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
        Selenide.refresh();

        step("Открыто главное меню с проектами", () -> {
            $(".BreadCrumbs ").shouldHave(text("Projects"));
        });
    }

    @Test
    @DisplayName("Создание тест кейса")
    void createTestCaseViaApiWithCheckViaUITest() {
        // API-взаимодействие вынести в отдельный класс. Чтобы не дублировать данный код в каждом тесте
        Response authResponse = step("Get authorization", () ->
                given().log().all()
                        .formParam("grant_type", "apitoken")
                        .formParam("scope", "openid")
                        .formParam("token", allureToken)
                        .when()
                        .post("/api/uaa/oauth/token")
                        .then().log().all()
                        .statusCode(200)
                        .extract().response());

        // И это также перенести в класс для API-взаимодействия
        String sessionCookieValue = step("Get session cookie value", () -> {
            String xsrfToken = authResponse.path("jti");
            return given().log().all()
                    .header("X-XSRF-TOKEN", xsrfToken)
                    .header("Cookie", "XSRF-TOKEN=" + xsrfToken)
                    .formParam("username", login)
                    .formParam("password", password)
                    .when()
                    .post("/api/login/system")
                    .then()
                    .statusCode(200).extract().response()
                    .getCookie("ALLURE_TESTOPS_SESSION");
        });

        CreateTestCaseResponse testCaseResponse = step("Create test case via api", () -> {
            CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
            testCaseBody.setName(faker.name().fullName());
            String accessToken = authResponse.path("access_token");
            return given().log().all()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + accessToken)
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
                    .body("name", is(testCaseBody.getName()))
                    .extract().as(CreateTestCaseResponse.class);
        });

        step("Add session cookie in browser", () -> {
            Selenide.open("https://allure.autotests.cloud/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("ALLURE_TESTOPS_SESSION", sessionCookieValue));
            Selenide.refresh();
        });

        step("Open test case url", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + projectId + "/test-cases/" + testCaseResponse.getId());
            // Проверяй, что душе угодно
            Selenide.sleep(5_000);
        });
    }
}