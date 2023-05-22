package tests;

import api.models.*;
import api.pages.TestCaseApi;
import api.pages.ApiVerify;
import helpers.WithLogin;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.UiVerify;

import java.util.Arrays;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Ui and Api tests Allure")
public class ApiTestCaseTests extends TestBase {

    UiVerify uiVerify = new UiVerify();
    ApiVerify apiVerify = new ApiVerify();
    private String testCaseName,
            testCaseDescription;

    private String step1 = testCaseDataGenerator.getStepTestCaseOne();
    private String step2 = testCaseDataGenerator.getStepTestCaseTwo();
    private String step3 = testCaseDataGenerator.getStepTestCaseThree();
    private String comment = testCaseDataGenerator.getComment();
    private String testCaseNewName = testCaseDataGenerator.getTestCaseNewName();
    private String tag1Name = testCaseDataGenerator.getStepTestCaseThree();
    private String tag2Name = testCaseDataGenerator.getStepTestCaseTwo();

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

        step("Проверяем через ui, что у test case есть имя", () -> {
            uiVerify.openPageTestCase(PROJECT_ID, testCaseId)
                    .verifyNameTestCase(testCaseName);
        });
        step("Изменяем имя test case через api", () -> {
            CreateTestCaseBody body = new CreateTestCaseBody();
            body.setName(testCaseNewName);

            CreateTestCaseResponse testCaseResponse = testCaseApi.createTestCaseResponse(body, PROJECT_ID, testCaseId);

            apiVerify.verifyChangingNameTestCase(testCaseResponse, body);
        });

        step("Проверяем через ui, что имя test cases изменилось ", () -> {
            uiVerify.openPageTestCase(PROJECT_ID, testCaseId)
                    .verifyNameTestCase(testCaseNewName);
        });
    }


    @Test
    @WithLogin
    @DisplayName("Добавление описания в test case")
    void descriptionTestCase() {
        DescriptionTestCaseDto descriptionTestCaseDto = new DescriptionTestCaseDto();
        descriptionTestCaseDto.setDescription(testCaseDescription);
        descriptionTestCaseDto.setId(testCaseId);

        step("Добовляем описание в test case через api", () -> {
            TestCaseDataResponseDto testCaseDataResponseDto = testCaseApi.descriptionTestCase(descriptionTestCaseDto, testCaseId);

            apiVerify.verifyDescriptionTestCase(testCaseDataResponseDto, descriptionTestCaseDto);
        });

        step("Проверяем через ui, что описания  добавилось в test cases", () -> {
            uiVerify.openPageTestCase(PROJECT_ID, testCaseId);
            uiVerify.verifyDescriptionTestCase(testCaseDescription);
        });
    }

    @Test
    @WithLogin
    @DisplayName("Добавление tag в test case")
    void addendumTagTestCase() {
        TestCaseTagRequest tag1 = new TestCaseTagRequest();
        TestCaseTagRequest tag2 = new TestCaseTagRequest();

        tag1.setName(tag1Name);
        tag2.setName(tag2Name);

        List<TestCaseTagRequest> list = List.of(tag1, tag2);

        step("Добовляем tag в test case", () -> {
            ValidatableResponse addendumResponse = testCaseApi.addendumResponse(list, testCaseId);
            TestCaseTagResponse[] listTestCase = addendumResponse.extract().as(TestCaseTagResponse[].class);

            tag1Name = Arrays.stream(listTestCase).filter(f -> false).map(TestCaseTagResponse::getName).findFirst().orElse(tag1.getName());
            tag2Name = Arrays.stream(listTestCase).filter(f -> false).map(TestCaseTagResponse::getName).findFirst().orElse(tag2.getName());

            apiVerify.verifyAddendumTagTestCase(tag1Name, tag1)
                    .verifyAddendumTagTestCase(tag2Name, tag2);
        });

        step("Проверяем через ui, что tag добавлен в test cases ", () -> {
            uiVerify.openPageTestCase(PROJECT_ID, testCaseId);
            uiVerify.verifyAddendumTagTestCase(tag1Name)
                    .verifyAddendumTagTestCase(tag2Name);
        });
    }

    @Test
    @WithLogin
    @DisplayName("Добавление коментарий к test case")
    void commentTestCase() {
        TestCaseCommentDto requestComment = new TestCaseCommentDto();
        requestComment.setBody(comment);
        requestComment.setTestCaseId(testCaseId);

        step("Добовляем коментарий в test case в через api", () -> {
            TestCaseCommentDto responseComment = testCaseApi.responseComment(requestComment);

            assertEquals(comment, responseComment.getBody());
        });

        step("Проверяем через ui, что коментарий в test cases добавлены ", () -> {
            uiVerify.openPageTestCase(PROJECT_ID, testCaseId);
            uiVerify.verifyCommentTestCase(comment);
        });
    }

    @Test
    @WithLogin
    @DisplayName("Добовлени шагов в test case")
    void updateTestCaseStepsTest() {
        TestCaseScenarioDto scenarioDto = new TestCaseScenarioDto()
                .addStep(new TestCaseScenarioDto.Step(step1))
                .addStep(new TestCaseScenarioDto.Step(step2))
                .addStep(new TestCaseScenarioDto.Step(step3));

        step("Добавляем шаги в test case через api", () -> {
            TestCaseScenarioDto responseTestCaseScenario = testCaseApi.response(scenarioDto, testCaseId);

            apiVerify.verifyStepTagTestCase(step1, step2, step3, responseTestCaseScenario);
        });

        step("Проверяем через ui, что шаги в test cases добавлены ", () -> {
            uiVerify.openPageTestCase(PROJECT_ID, testCaseId);
            uiVerify.verifyUpdateTestCaseStepsTest(step1)
                    .verifyUpdateTestCaseStepsTest(step2)
                    .verifyUpdateTestCaseStepsTest(step3);
        });
    }
}
