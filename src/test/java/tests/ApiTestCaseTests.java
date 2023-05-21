package tests;

import api.authorization.AuthorizationApi;
import api.models.*;
import api.pages.TestCaseApi;
import com.codeborne.selenide.Selenide;
import helpers.WithLogin;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static api.models.specs.Specs.request;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Ui and Api tests Allure")
public class ApiTestCaseTests extends TestBase {
    String testCaseName,
            testCaseNewName,
            testCaseDescription,
            step1,
            step2,
            step3,
            comment,
            tag1Name,
            tag2Name;
    Long testCaseId;

    private final TestCaseApi testCaseApi = new TestCaseApi();
    private static final Long PROJECT_ID = 2237L;

    @BeforeEach
    public void createTestCase() {
        this.testCaseName = testCaseDataGenerator.getTestCaseName();
        this.testCaseDescription = testCaseDataGenerator.getTestDescription();

        CreateTestCaseRequestDto testCase = CreateTestCaseRequestDto.builder()
                .name(testCaseName)
                .description(testCaseDescription)
                .projectId(PROJECT_ID)
                .build();

        TestCaseDataResponseDto testCaseResponseData = testCaseApi.createTestCase(testCase);

        assertThat(testCaseResponseData.getId()).isNotNull();

        this.testCaseId = testCaseResponseData.getId();
        this.step1 = testCaseDataGenerator.getStepTestCaseOne();
        this.step2 = testCaseDataGenerator.getStepTestCaseTwo();
        this.step3 = testCaseDataGenerator.getStepTestCaseThree();
        this.comment = testCaseDataGenerator.getComment();
        this.testCaseNewName = testCaseDataGenerator.getTestCaseNewName();
    }

    @AfterEach
    public void deleteTestCase() {
        testCaseApi.deleteTestCase(testCaseId);
    }

    @Test
    @WithLogin
    @DisplayName("Редактирование имени test case")
    void changingNameTestCase() {
        step("Проверяем, что у созданного test case есть имя", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $(".TestCaseLayout__name").shouldHave(text(testCaseName));
        });

        CreateTestCaseBody body = new CreateTestCaseBody();
        body.setName(testCaseNewName);

        CreateTestCaseResponse testCaseResponse = testCaseApi.createTestCaseResponse(body, PROJECT_ID, testCaseId);

        step("Api verify  changing in response", () ->
                assertThat(testCaseResponse.getName()).isEqualTo(body.getName()));

        step("Проверяем, что changing test cases изменилось ", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $(".TestCaseLayout__name").shouldHave(text(testCaseNewName));
        });
    }

    @Test
    @WithLogin
    @DisplayName("Добавление описания в test case")
    void descriptionTestCase() {
        DescriptionTestCaseDto descriptionTestCaseDto = new DescriptionTestCaseDto();
        descriptionTestCaseDto.setDescription(testCaseDescription);
        descriptionTestCaseDto.setId(testCaseId);

        TestCaseDataResponseDto testCaseDataResponseDto = testCaseApi.descriptionTestCase(descriptionTestCaseDto, testCaseId);

        step("Api verify  description in response", () ->
                assertThat(testCaseDataResponseDto.getDescription()).isEqualTo(descriptionTestCaseDto.getDescription()));
        step("Проверяем, что description test cases добавлены ", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $("[data-testid='section__description']").shouldHave(text(testCaseDescription)).shouldHave(visible);
        });
    }

    @Test
    @WithLogin
    @DisplayName("Добавление tag к test case")
    void addendumTagTestCase() {
        TestCaseTagDto tag1 = new TestCaseTagDto();
        TestCaseTagDto tag2 = new TestCaseTagDto();
        tag1.setId(166L);
        tag1.setName("API");
        tag2.setId(1052L);
        tag2.setName("REGRESS");

        List<TestCaseTagDto> list = List.of(tag1, tag2);

        ValidatableResponse addendumResponse = testCaseApi.addendumResponse(list, testCaseId);
        TestCaseTagDto[] listTestCase = addendumResponse.extract().as(TestCaseTagDto[].class);


        this.tag1Name = Arrays.stream(listTestCase).filter(f -> f.getId().equals(tag1.getId())).map(TestCaseTagDto::getName).findFirst().orElse(tag1.getName());
        var tag1Id = Arrays.stream(listTestCase).filter(f -> f.getName().equals(tag1.getName())).map(TestCaseTagDto::getId).findFirst().orElse(tag1.getId());
        this.tag2Name = Arrays.stream(listTestCase).filter(f -> f.getId().equals(tag2.getId())).map(TestCaseTagDto::getName).findFirst().orElse(tag2.getName());
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
    @DisplayName("Добавление comment test case")
    void commentTestCase() {
        TestCaseCommentDto requestComment = new TestCaseCommentDto();
        requestComment.setBody(comment);
        requestComment.setTestCaseId(testCaseId);

        TestCaseCommentDto responseComment = testCaseApi.responseComment(requestComment);

        step("Api verify comment in response", () ->
                assertEquals(comment, responseComment.getBody()));

        step("Проверяем, comment в test cases добавлены ", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
            $(".Comment__body").shouldHave(text(comment)).shouldHave(visible);
        });
    }

    @Test
    @WithLogin
    @DisplayName("Обновление шагов в test case")
    void updateTestCaseStepsTest2() {
        TestCaseScenarioDto scenarioDto = new TestCaseScenarioDto()
                .addStep(new TestCaseScenarioDto.Step(step1, "st-1"))
                .addStep(new TestCaseScenarioDto.Step(step2, "st-2"))
                .addStep(new TestCaseScenarioDto.Step(step3, "st-3"));

        TestCaseScenarioDto responseTestCaseScenario = testCaseApi.response(scenarioDto, testCaseId);

        step("Api verify step 1 in response", () ->
                assertEquals(step1, responseTestCaseScenario.getSteps().get(0).getName()));
        step("Api verify name step2 in response", () ->
                assertEquals(step2, responseTestCaseScenario.getSteps().get(1).getName()));
        step("Api verify name  step3 in response", () ->
                assertEquals(step3, responseTestCaseScenario.getSteps().get(2).getName()));

        step("Open test case url", () -> {
            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
        });

        step("UI verify step in test case ", () -> {
            $x("//pre[text()='" + step1 + "']").shouldHave(text(step3)).shouldHave(visible);
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
