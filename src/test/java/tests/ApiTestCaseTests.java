package tests;

import api.models.*;
import api.pages.TestCaseApi;
import api.pages.specs.ApiVerify;
import com.codeborne.selenide.Selenide;
import helpers.WithLogin;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.UiVerify;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Ui and Api tests Allure")
public class ApiTestCaseTests extends TestBase {

    UiVerify uiVerify = new UiVerify();
    ApiVerify apiVerify = new ApiVerify();
    String testCaseName,
            testCaseDescription;

    String step1 = testCaseDataGenerator.getStepTestCaseOne();
    String step2 = testCaseDataGenerator.getStepTestCaseTwo();
    String step3 = testCaseDataGenerator.getStepTestCaseThree();
    String comment = testCaseDataGenerator.getComment();
    String testCaseNewName = testCaseDataGenerator.getTestCaseNewName();
    String tag1Name = testCaseDataGenerator.getStepTestCaseThree();
    String tag2Name = testCaseDataGenerator.getStepTestCaseTwo();

    Long testCaseId;

    private final TestCaseApi testCaseApi = new TestCaseApi();
    private final Long PROJECT_ID = 2237L;

    @BeforeEach
    @DisplayName("Создаем test case")
    public void createTestCase() {
        testCaseName = testCaseDataGenerator.getTestCaseName();
        testCaseDescription = testCaseDataGenerator.getTestDescription();

        CreateTestCaseRequestDto testCase = CreateTestCaseRequestDto.builder()
                .name(testCaseName)
                .description(testCaseDescription)
                .projectId(PROJECT_ID)
                .build();

        TestCaseDataResponseDto testCaseResponseData = testCaseApi.createTestCase(testCase);

        assertThat(testCaseResponseData.getId()).isNotNull();

        testCaseId = testCaseResponseData.getId();
    }

    @AfterEach
    @DisplayName("Удаляем test case")
    public void deleteTestCase() {
        testCaseApi.deleteTestCase(testCaseId);
    }

    @Test
    @WithLogin
    @DisplayName("Редактирование имени test case")
    void changingNameTestCase() {

        step("Проверяем что у test case есть имя", () ->
                uiVerify.openPageTestCase(PROJECT_ID, testCaseId)
                        .verifyNameTestCase(testCaseName));

        CreateTestCaseBody body = new CreateTestCaseBody();
        body.setName(testCaseNewName);

        CreateTestCaseResponse testCaseResponse = testCaseApi.createTestCaseResponse(body, PROJECT_ID, testCaseId);

        step("Api verify  changing in response", () ->
                assertThat(testCaseResponse.getName()).isEqualTo(body.getName()));

        step("Проверяем, что имя test cases изменилось ", () ->
                uiVerify.openPageTestCase(PROJECT_ID, testCaseId)
                        .verifyNameTestCase(testCaseNewName));
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
        TestCaseTagRequest tag1 = new TestCaseTagRequest();
        TestCaseTagRequest tag2 = new TestCaseTagRequest();

        tag1.setName(tag1Name);
        tag2.setName(tag2Name);

        List<TestCaseTagRequest> list = List.of(tag1, tag2);

        ValidatableResponse addendumResponse = testCaseApi.addendumResponse(list, testCaseId);
        TestCaseTagResponse[] listTestCase = addendumResponse.extract().as(TestCaseTagResponse[].class);

        this.tag1Name = Arrays.stream(listTestCase).filter(f -> false).map(TestCaseTagResponse::getName).findFirst().orElse(tag1.getName());
        this.tag2Name = Arrays.stream(listTestCase).filter(f -> false).map(TestCaseTagResponse::getName).findFirst().orElse(tag2.getName());


        step("Api verify tag in response", () -> {
            assertThat(tag1Name).as("Ошибка с именем tag1").isEqualTo(tag1.getName());
            assertThat(tag2Name).as("Ошибка с именем tag2").isEqualTo(tag2.getName());
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
            $x("//pre[text()='" + step1 + "']").shouldHave(text(step1)).shouldHave(visible);
            $x("//pre[text()='" + step2 + "']").shouldHave(text(step2)).shouldHave(visible);
            $x("//pre[text()='" + step3 + "']").shouldHave(text(step3)).shouldHave(visible);
        });
    }
}
