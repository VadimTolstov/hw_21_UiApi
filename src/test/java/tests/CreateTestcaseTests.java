package tests;

import authorization.AuthorizationApi;
import com.codeborne.selenide.Selenide;
import helpers.WithLogin;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import models.CreateTestCaseBody;
import models.CreateTestCaseResponse;
import models.TestCaseScenarioDto;
import models.specs.CreateTestCaseRequestDto;
import models.specs.TestCaseDataResponseDto;
import org.junit.jupiter.api.*;
import testcase.TestCaseApi;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Ui and Api tests Allure")
public class CreateTestcaseTests extends TestBase {
    String testCaseName,
            testCaseDescription,
            accessToken;
    Long testCaseId;

    private final TestCaseApi testCaseApi = new TestCaseApi();
    private static final Long PROJECT_ID = 2237L;

    @BeforeEach
    @Step("Создание test cesa по api")
    public void createTestCase() {
        this.testCaseName = testCaseApiDataGenerator.getTestCaseName();
        this.testCaseDescription = testCaseApiDataGenerator.getTestDescription();

        CreateTestCaseRequestDto testCase = CreateTestCaseRequestDto.builder()
                .name(testCaseName)
                .description(testCaseDescription)
                .projectId(PROJECT_ID)
                .build();

        TestCaseDataResponseDto testCaseResponseData = testCaseApi.createTestCase(testCase);

        assertThat(testCaseResponseData.getId()).isNotNull();

        this.testCaseId = testCaseResponseData.getId();

        this.accessToken = AuthorizationApi.getAuthorization().getAccessToken();
    }

    @AfterEach
    @Step("Удаляем test cesa по api")
    public void deleteTestCase() {
        testCaseApi.deleteTestCase(testCaseId);
    }

    @Test
    @WithLogin
    @DisplayName("Обновление шагов тест-кейса V2.0")
    void updateTestCaseStepsTest2() {
        String step1 = testCaseApiDataGenerator.getStepTestCaseOne();
        String step2 = testCaseApiDataGenerator.getStepTestCaseTwo();
        String step3 = testCaseApiDataGenerator.getStepTestCaseThree();

        TestCaseScenarioDto scenarioDto = new TestCaseScenarioDto()
                .addStep(new TestCaseScenarioDto.Step(step1, "st-1"))
                .addStep(new TestCaseScenarioDto.Step(step2, "st-2"))
                .addStep(new TestCaseScenarioDto.Step(step3, "st-3"));

        TestCaseScenarioDto response = step("Добовляем в test case steps по api", () ->
                given().log().all()
                        .filter(withCustomTemplates())
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + accessToken)
                        .body(scenarioDto)
                        .when()
                        .post("/api/rs/testcase/" + testCaseId + "/scenario")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(TestCaseScenarioDto.class));

        step("Api verify step 1 in response", () ->
                assertEquals(step1, response.getSteps().get(0).getName()));
        step("Api verify name step2 in response", () ->
                assertEquals(step2, response.getSteps().get(1).getName()));
        step("Api verify name  step3 in response", () ->
                assertEquals(step3, response.getSteps().get(2).getName()));

        step("Open test case url", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $x("//pre[text()='" + step1 + "']").shouldHave(text(step1));
        });

        step("UI verify step in test case ", () -> {
            $x("//pre[text()='" + step1 + "']").shouldHave(text(step1)).shouldHave(visible);
            $x("//pre[text()='" + step2 + "']").shouldHave(text(step2)).shouldHave(visible);
            $x("//pre[text()='" + step3 + "']").shouldHave(text(step3)).shouldHave(visible);
        });
    }

    @Test
    @Disabled("Устарел, не работает с @BeforeEach, кейс создается в тесте")
    @WithLogin
    @DisplayName("Обновление шагов тест-кейса")
    void updateTestCaseStepsTest1() {
        String step1 = "Step 1";
        String step2 = "Step 2";
        String step3 = "Step 3";

        CreateTestCaseResponse testCaseResponse = step("Создаем test case по api", () -> {
            CreateTestCaseBody testCaseBody = new CreateTestCaseBody();
            testCaseBody.setName(testCaseName);
            return given()
                    .filter(withCustomTemplates())
                    .log().all()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer " + AuthorizationApi.getAuthorization().getAccessToken())
                    .body(testCaseBody)
                    .queryParam("projectId", PROJECT_ID)
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

        // Создаем тестовые данные - нашу модель с шагами теста
        TestCaseScenarioDto scenarioDto = new TestCaseScenarioDto()
                .addStep(new TestCaseScenarioDto.Step(step1, "st-1"))
                .addStep(new TestCaseScenarioDto.Step(step2, "st-2"))
                .addStep(new TestCaseScenarioDto.Step(step3, "st-3"));

                TestCaseScenarioDto response = step("Добовляем в test case steps по api", () ->
                given().log().all()
                        .filter(withCustomTemplates())
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer " + AuthorizationApi.getAuthorization().getAccessToken())
                        .body(scenarioDto)
                        .when()
                        .post("/api/rs/testcase/" + testCaseResponse.getId() + "/scenario")
                        .then()
                        .log().all()
                        .statusCode(200)
                        .extract().as(TestCaseScenarioDto.class)
        );
        // Теперь в переменной response содержится ответ сервера. Можем с ним что-нибудь сделать
        step("Api verify  name[0] in response", () ->
                assertEquals("Step 1", response.getSteps().get(0).getName()));
        step("Api verify  name[1] in response", () ->
                assertEquals("Step 2", response.getSteps().get(1).getName()));
        step("Api verify  name[2] in response", () ->
                assertEquals("Step 3", response.getSteps().get(2).getName()));

        step("Open test case url", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseResponse.getId());
            $x("//pre[text()='" + step1 + "']").shouldHave(text(step1));
        });

        step("UI verify step in test case ", () -> {
            $x("//pre[text()='" + step1 + "']").shouldHave(text(step1)).shouldHave(visible);
            $x("//pre[text()='" + step2 + "']").shouldHave(text(step2)).shouldHave(visible);
            $x("//pre[text()='" + step3 + "']").shouldHave(text(step3)).shouldHave(visible);
        });
    }
}