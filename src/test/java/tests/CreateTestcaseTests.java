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
    void createWitUIOnlyTest() {
        Faker faker = new Faker();
        String testCaseName = faker.name().fullName();

        step("Authorize", () -> {
            open("/");
            $(byName("username")).setValue(login);
            $(byName("password")).setValue(password);
            $("button[type='submit']").click();
            $(byName("username")).setValue(login);
            $(byName("password")).setValue(password);
            $("button[type='submit']").click();

        });
        step("Go to project", () -> {
            open("/project/2237/test-cases");
            open("/project/2237/test-cases");
        });

        step("Create testcase", () -> {
            $("[data-testid=input__create_test_case]").setValue(testCaseName)
                    .pressEnter();
        });

        step("Verify testcase name", () -> {
            $(".LoadableTree__view").shouldHave(text(testCaseName));
        });
    }

    @Test
    void createWitApiOnlyTest() {
        Faker faker = new Faker();
        String testCaseName = faker.name().fullName();

//        step("Authorize", () -> {
//            open("/");
//            $(byName("username")).setValue(login);
//            $(byName("password")).setValue(password);
//            $("button[type='submit']").click();
//        });
//        step("Go to project", () -> {
//            open("/project/2220/test-cases");
//        });

        step("Create testcase", () -> {
            CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
            testCaseBody.setName(testCaseName);

            given()
                    .log().all()
                    .header("X-XSRF-TOKEN", "d524ce6f-b1e7-4980-a362-af0780af3337")
                    .cookies("XSRF-TOKEN", "d524ce6f-b1e7-4980-a362-af0780af3337",
                            "ALLURE_TESTOPS_SESSION", "750321c7-a8f1-4645-97fd-e1404be964d5")
                    .contentType("application/json;charset=UTF-8")
                    .body(testCaseBody)
                    .queryParam("projectId", projectId)
                    .when()
                    .post("/api/rs/testcasetree/leaf")
                    .then()
                    .log().status()
                    .log().all()
                    .statusCode(200)
                    .body("statusName", is("Draft"))
                    .body("name", is(testCaseName));
        /*

  -H 'Cookie: XSRF-TOKEN=9b5cd8b4-8b79-4f0b-9444-2360d68184e4; ALLURE_TESTOPS_SESSION=20af13f6-96b3-4cd3-8d35-b5cfe01c442c' \
  -H 'Origin: https://allure.autotests.cloud' \
            -H 'Sec-Fetch-Dest: empty' \
            -H 'Sec-Fetch-Mode: cors' \
            -H 'Sec-Fetch-Site: same-origin' \
            -H 'User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36' \
            -H 'X-XSRF-TOKEN: 9b5cd8b4-8b79-4f0b-9444-2360d68184e4' \
            -H 'sec-ch-ua: "Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"' \
            -H 'sec-ch-ua-mobile: ?0' \
            -H 'sec-ch-ua-platform: "macOS"' \
            --data-raw '{"name":"test test 3"}' \
            --compressed
         */

//           curl 'https://allure.autotests.cloud/api/rs/testcasetree/leaf?projectId=2237&treeId=&' \
//            -H 'Accept: application/json, text/plain, */*' \
//            -H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' \
//            -H 'Cache-Control: no-cache' \
//            -H 'Connection: keep-alive' \
//            -H 'Content-Type: application/json;charset=UTF-8' \
//            -H 'Cookie: XSRF-TOKEN=d524ce6f-b1e7-4980-a362-af0780af3337; ALLURE_TESTOPS_SESSION=750321c7-a8f1-4645-97fd-e1404be964d5' \
//            -H 'Origin: https://allure.autotests.cloud' \
//            -H 'Pragma: no-cache' \
//            -H 'Sec-Fetch-Dest: empty' \
//            -H 'Sec-Fetch-Mode: cors' \
//            -H 'Sec-Fetch-Site: same-origin' \
//            -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36' \
//            -H 'X-XSRF-TOKEN: d524ce6f-b1e7-4980-a362-af0780af3337' \
//            -H 'sec-ch-ua: "Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"' \
//            -H 'sec-ch-ua-mobile: ?0' \
//            -H 'sec-ch-ua-platform: "Windows"' \
//            --data-raw '{"name":"121"}' \
//            --compressed


//            $("[data-testid=input__create_test_case]").setValue(testCaseName)
//                    .pressEnter();
        });

        step("Verify testcase name", () -> {
//            $(".LoadableTree__view").shouldHave(text(testCaseName));
        });
    }

    public String id;

    @Test
    void editingTestUsingApi() {
        Faker faker = new Faker();
        String testCaseName = faker.name().fullName();


        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponse createTestCaseResponse = step("Create testcase", () ->
                given()
                        .log().all()
                        .header("X-XSRF-TOKEN", "9b5cd8b4-8b79-4f0b-9444-2360d68184e4")
                        .cookies("XSRF-TOKEN", "9b5cd8b4-8b79-4f0b-9444-2360d68184e4",
                                "ALLURE_TESTOPS_SESSION", "20af13f6-96b3-4cd3-8d35-b5cfe01c442c")
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
            Assertions.assertThat(createTestCaseResponse.getName()).isEqualTo(testCaseName);
//            $(".LoadableTree__view").shouldHave(text(testCaseName));
        });
//   curl 'https://allure.autotests.cloud/api/rs/testcasetree/leaf/rename?projectId=2237&&search=W3siaWQiOiJzdGF0dXMiLCJ0eXBlIjoibG9uZ0FycmF5IiwidmFsdWUiOlsiLTEiXX1d&leafId=17969' \
//  -H 'Accept: application/json, text/plain, */*' \
//  -H 'Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7' \
//  -H 'Cache-Control: no-cache' \
//  -H 'Connection: keep-alive' \
//  -H 'Content-Type: application/json;charset=UTF-8' \
//  -H 'Cookie: XSRF-TOKEN=d524ce6f-b1e7-4980-a362-af0780af3337; ALLURE_TESTOPS_SESSION=750321c7-a8f1-4645-97fd-e1404be964d5' \
//  -H 'Origin: https://allure.autotests.cloud' \
//  -H 'Pragma: no-cache' \
//  -H 'Sec-Fetch-Dest: empty' \
//  -H 'Sec-Fetch-Mode: cors' \
//  -H 'Sec-Fetch-Site: same-origin' \
//  -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36' \
//  -H 'X-XSRF-TOKEN: d524ce6f-b1e7-4980-a362-af0780af3337' \
//  -H 'sec-ch-ua: "Chromium";v="112", "Google Chrome";v="112", "Not:A-Brand";v="99"' \
//  -H 'sec-ch-ua-mobile: ?0' \
//  -H 'sec-ch-ua-platform: "Windows"' \
//  --data-raw '{"name":"Carroll"}' \
//  --compressed
    }

    @Test
    void editingTestUsingApi2() {
        Faker faker = new Faker();
        String testCaseName = faker.name().fullName();


        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponse createTestCaseResponse = step("Create testcase", () ->
                given()
                        .log().all()
                        .header("X-XSRF-TOKEN", "01391561-7eea-4e9b-9c11-4f333247b0f3")
                        .cookies("XSRF-TOKEN", "01391561-7eea-4e9b-9c11-4f333247b0f3",
                                "ALLURE_TESTOPS_SESSION", "3b6566d3-d1e7-4ea9-8199-ab65ce763a1d")
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

            Cookie authoriztionCookie = new Cookie("ALLURE_TESTOPS_SESSION", "3b6566d3-d1e7-4ea9-8199-ab65ce763a1d");
            getWebDriver().manage().addCookie(authoriztionCookie);

            Integer testCesaId = createTestCaseResponse.getId();
            String testCaseUrl = format("/project/%s/test-cases/%s?", projectId, testCesaId);
            open(testCaseUrl);

            $(".TestCaseLayout__name").shouldHave(text(testCaseName));
        });
    }

    @Test
    void createWitApiUIExtendedTest() {
        Faker faker = new Faker();
        String testCaseName = faker.name().fullName();


        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponse createTestCaseResponse = step("Create testcase", () ->
                given()
                        .log().all()
                        .header("X-XSRF-TOKEN", "01391561-7eea-4e9b-9c11-4f333247b0f3")
                        .cookies("XSRF-TOKEN", "01391561-7eea-4e9b-9c11-4f333247b0f3",
                                "ALLURE_TESTOPS_SESSION", "3b6566d3-d1e7-4ea9-8199-ab65ce763a1d")
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

            Cookie authoriztionCookie = new Cookie("ALLURE_TESTOPS_SESSION", "3b6566d3-d1e7-4ea9-8199-ab65ce763a1d");
            getWebDriver().manage().addCookie(authoriztionCookie);

            Integer testCesaId = createTestCaseResponse.getId();
            String testCaseUrl = format("/project/%s/test-cases/%s?", projectId, testCesaId);
            open(testCaseUrl);

            $(".TestCaseLayout__name").shouldHave(text(testCaseName));
        });
    }

    @Test
    void createWitApiUIExtendedTest33() {
//        Faker faker = new Faker();
//        String testCaseName = faker.name().fullName();
//
//
//        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
//        testCaseBody.setName(testCaseName);

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

//    }
//                .contentType("application/json;charset=UTF-8")
//                .body(testCaseBody)
//                .queryParam("projectId", projectId)
//                .when()
//                .post("/api/rs/testcasetree/leaf")
//                .then()
//                .log().status()
//                .log().body()
//                .log().all()
//                .statusCode(200)
//                .body("statusName", is("Draft"))
//                .body("name", is(testCaseName))
//                .extract().as(CreateTestCaseResponse.class));
//
//        step("Verify testcase name", () -> {
//            open("/favicon.ico");
//
//            Cookie authoriztionCookie = new Cookie("ALLURE_TESTOPS_SESSION", "3b6566d3-d1e7-4ea9-8199-ab65ce763a1d");
//            getWebDriver().manage().addCookie(authoriztionCookie);
//
//            Integer testCesaId = createTestCaseResponse.getId();
//            String testCaseUrl = format("/project/%s/test-cases/%s?", projectId, testCesaId);
//            open(testCaseUrl);
//
//            $(".TestCaseLayout__name").shouldHave(text(testCaseName));
//        });
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
//        Faker faker = new Faker();
//        String testCaseName = faker.name().fullName();
//
//
//        CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
//        testCaseBody.setName(testCaseName);

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

//                .contentType(ContentType.MULTIPART)
//                .cookie("XSRF-TOKEN", xsrfTofen)
//                .multiPart("username", "allure8")
//                .multiPart("password", " allure8")
//                .post("https://allure.autotests.cloud/api/login/system")
//                .then().log().all().extract().cookie("ALLURE_TESTOPS_SESSION");

        Date expDate = new Date();
        expDate.setTime(expDate.getTime() + (10000 * 10000));
        Cookie cookie = new Cookie("ALLURE_TESTOPS_SESSION", sessionId, "allure.autotests.cloud", "/", expDate);
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
        Selenide.refresh();

    }

}