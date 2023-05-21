package tests;

import authorization.AuthorizationApi;
import com.codeborne.selenide.Selenide;
import helpers.WithLogin;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import models.*;
import models.specs.CreateTestCaseRequestDto;
import models.specs.Specs;
import models.specs.TestCaseDataResponseDto;
import org.junit.jupiter.api.*;
import testcase.TestCaseApi;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static models.specs.Specs.request;
import static models.specs.Specs.response;
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
    @DisplayName("Редактирование имени тест кейса")
    void changingNameTestCase() {
        step("Проверяем, что у созданного test cases есть имя", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $(".TestCaseLayout__name").shouldHave(text(testCaseName));
        });

        String changing = testCaseApiDataGenerator.getTestCaseName();
        CreateTestCaseBody body = new CreateTestCaseBody();
        body.setName(changing);
        CreateTestCaseResponse createTestCaseResponse = step("Редактируем тест кейс", () ->
                given(request)
                        .queryParam("projectId", PROJECT_ID)
                        .queryParam("leafId", testCaseId)
                        .body(body)
                        .when()
                        .post("testcasetree/leaf/rename")
                        .then()
                        .spec(response)
                        .extract().as(CreateTestCaseResponse.class));

        step("Api verify  changing in response", () ->
                assertEquals(changing, createTestCaseResponse.getName()));

        step("Проверяем, что changing test cases изменилось ", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $(".TestCaseLayout__name").shouldHave(text(createTestCaseResponse.getName()));
        });
    }

    @Test
    @WithLogin
    @DisplayName("Добавление описания test cases")
    void descriptionTestCase() {
        String description = testCaseApiDataGenerator.getTestDescription();
        DescriptionTestCaseDto descriptionTestCaseDto = new DescriptionTestCaseDto();
        descriptionTestCaseDto.setDescription(description);
        descriptionTestCaseDto.setId(testCaseId);

        TestCaseDataResponseDto testCaseDataResponseDto = step("Добавляем описание test cases", () ->
                given(request)
                        .body(descriptionTestCaseDto)
                        .when()
                        .patch("testcase/" + testCaseId)
                        .then().spec(response)
                        .extract().as(TestCaseDataResponseDto.class));

        step("Api verify  description in response", () ->
                assertEquals(description, testCaseDataResponseDto.getDescription()));

        step("Проверяем, что description test cases добавлены ", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $("[data-testid='section__description']").shouldHave(text(testCaseDataResponseDto.getDescription()));
        });
    }

    @Test
    @WithLogin
    @DisplayName("Добавление tag к test cases")
    void addendumTagTestCase() {
        TestCaseTagDto tag1 = new TestCaseTagDto();
        TestCaseTagDto tag2 = new TestCaseTagDto();
        tag1.setId(166L);
        tag1.setName("API");
        tag2.setId(1052L);
        tag2.setName("REGRESS");

        List<TestCaseTagDto> list = List.of(tag1, tag2);
        ValidatableResponse response = step("Добовляем tag в test cases", () ->
                given(request)
                        .body(list)
                        .when()
                        .post("testcase/" + testCaseId + "/tag")
                        .then().log().body());

        TestCaseTagDto[] listTestCase = response.extract().as(TestCaseTagDto[].class);


        var tag1Name = Arrays.stream(listTestCase).filter(f -> f.getId().equals(tag1.getId())).map(TestCaseTagDto::getName).findFirst().orElse(tag1.getName());
        var tag1Id = Arrays.stream(listTestCase).filter(f -> f.getName().equals(tag1.getName())).map(TestCaseTagDto::getId).findFirst().orElse(tag1.getId());
        var tag2Name = Arrays.stream(listTestCase).filter(f -> f.getId().equals(tag2.getId())).map(TestCaseTagDto::getName).findFirst().orElse(tag2.getName());
        var tag2Id = Arrays.stream(listTestCase).filter(f -> f.getName().equals(tag2.getName())).map(TestCaseTagDto::getId).findFirst().orElse(tag2.getId());

        step("Api verify tag in response", () -> {
            assertThat(tag1Name).as("Ошибка с именем tag1").isEqualTo(tag1.getName());
            assertThat(tag1Id).as("Ошибка с id tag1").isEqualTo(tag1.getId());
            assertThat(tag2Name).as("Ошибка с именем tag2").isEqualTo(tag2.getName());
            assertThat(tag2Id).as("Ошибка с id tag1").isEqualTo(tag2.getId());
        });

        step("Проверяем, что tag добавлен в test cases ", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $("[data-testid='section__tags']").shouldHave(text(tag1Name)).shouldHave(visible);
            $("[data-testid='section__tags']").shouldHave(text(tag2Name)).shouldHave(visible);
        });
    }

    @Test
    @WithLogin
    @DisplayName("Добавление comment test cases")
    void commentTestCase() {
        String comment = testCaseApiDataGenerator.getComment();
        TestCaseCommentDto testCaseCommentDto = new TestCaseCommentDto();
        testCaseCommentDto.setBody(comment);
        testCaseCommentDto.setTestCaseId(testCaseId);

        TestCaseCommentDto responseDto = step("Добовляем comment test cases", () ->
                given(request)
                        .body(testCaseCommentDto)
                        .when()
                        .post("comment")
                        .then()
                        .spec(response)
                        .extract().as(TestCaseCommentDto.class));

        step("Api verify comment in response", () ->
                assertEquals(comment, responseDto.getBody()));

        step("Проверяем, comment в test cases добавлены ", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $(".Comment__body").shouldHave(text(responseDto.getBody())).shouldHave(visible);
        });
    }

    @Test
    @WithLogin
    @DisplayName("Обновление шагов test cases V2.0")
    void updateTestCaseStepsTest2() {
        String step1 = testCaseApiDataGenerator.getStepTestCaseOne();
        String step2 = testCaseApiDataGenerator.getStepTestCaseTwo();
        String step3 = testCaseApiDataGenerator.getStepTestCaseThree();

        TestCaseScenarioDto scenarioDto = new TestCaseScenarioDto()
                .addStep(new TestCaseScenarioDto.Step(step1, "st-1"))
                .addStep(new TestCaseScenarioDto.Step(step2, "st-2"))
                .addStep(new TestCaseScenarioDto.Step(step3, "st-3"));

        TestCaseScenarioDto response = step("Добовляем в test case steps по api", () ->
                given(request)
                        .body(scenarioDto)
                        .when()
                        .post("testcase/" + testCaseId + "/scenario")
                        .then()
                        .spec(Specs.response)
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
