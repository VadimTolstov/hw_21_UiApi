package tests.allure.api;

import api.models.*;
import api.pages.TestCaseApi;
import helpers.ApiTest;
import helpers.Regress;
import helpers.WithLogin;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Ui and Api tests Allure")
public class ApiAllureTests extends ApiTestBase {

    private String testCaseName,
            testCaseDescription;

    Long testCaseId;

    private final TestCaseApi testCaseApi = new TestCaseApi();
    private final Long PROJECT_ID = 2237L;

    @BeforeEach
    @Epic("API")
    @Owner("толстов вадим")
    @DisplayName("Создаем test case")
    public void createTestCase() {
        testCaseName = dataGenerator.getRandomSentence(2);
        testCaseDescription = dataGenerator.getRandomSentence(3);

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
    @Epic("API")
    @Owner("толстов вадим")
    @DisplayName("Удаляем test case")
    public void deleteTestCase() {
        testCaseApi.deleteTestCase(testCaseId);
    }

    @Test
    @Epic("API")
    @Owner("толстов вадим")
    @ApiTest
    @Regress
    @WithLogin
    @DisplayName("Редактирование имени test case")
    void changingNameTestCase() {
        String testCaseNewName = dataGenerator.getRandomSentence(4);

        step("Проверяем через ui, что у test case есть имя", () -> {
            testCasePages.openPageTestCase(PROJECT_ID, testCaseId)
                    .verifyNameTestCase(testCaseName);
        });

        step("Изменяем имя test case через api", () -> {
            CreateTestCaseBody body = new CreateTestCaseBody();
            body.setName(testCaseNewName);

            CreateTestCaseResponse testCaseResponse = testCaseApi.createTestCaseResponse(body, PROJECT_ID, testCaseId);

            apiVerify.verifyChangingNameTestCase(testCaseResponse, body);
        });

        step("Проверяем через ui, что имя test case изменилось ", () -> {
            testCasePages.openPageTestCase(PROJECT_ID, testCaseId)
                    .verifyNameTestCase(testCaseNewName);
        });
    }

    @Test
    @Epic("API")
    @Owner("толстов вадим")
    @ApiTest
    @Regress
    @WithLogin
    @DisplayName("Добавляем описания в test case")
    void descriptionTestCase() {
        String testCaseNewDescription = dataGenerator.getRandomSentence(4);

        step("Проверяем через ui, что у  test cases есть описание", () -> {
            testCasePages.openPageTestCase(PROJECT_ID, testCaseId)
                    .verifyDescriptionTestCase(testCaseDescription);
        });

        DescriptionTestCaseDto descriptionTestCaseDto = new DescriptionTestCaseDto();
        descriptionTestCaseDto.setDescription(testCaseNewDescription);
        descriptionTestCaseDto.setId(testCaseId);

        step("Добавляем описание в test case через api", () -> {
            TestCaseDataResponseDto testCaseDataResponseDto = testCaseApi.descriptionTestCase(descriptionTestCaseDto, testCaseId);

            apiVerify.verifyDescriptionTestCase(testCaseDataResponseDto, descriptionTestCaseDto);
        });

        step("Проверяем через ui, что описания  поменялось в test case", () -> {
            testCasePages.openPageTestCase(PROJECT_ID, testCaseId);
            testCasePages.verifyDescriptionTestCase(testCaseNewDescription);
        });
    }

    @Test
    @Epic("API")
    @Owner("толстов вадим")
    @ApiTest
    @Regress
    @WithLogin
    @DisplayName("Добавление tag в test case")
    void addendumTagTestCase() {
        String tag1Name = dataGenerator.getRandomSentence(1);
        String tag2Name = dataGenerator.getRandomSentence(2);

        TestCaseTagRequest tag1 = new TestCaseTagRequest();
        TestCaseTagRequest tag2 = new TestCaseTagRequest();

        tag1.setName(tag1Name);
        tag2.setName(tag2Name);

        List<TestCaseTagRequest> list = List.of(tag1, tag2);

        step("Добавляем tag в test case", () -> {
            ValidatableResponse addendumResponse = testCaseApi.addendumResponse(list, testCaseId);
            TestCaseTagResponse[] listTestCase = addendumResponse.extract().as(TestCaseTagResponse[].class);

            String tag1NameV = Arrays.stream(listTestCase).filter(f -> false).map(TestCaseTagResponse::getName).findFirst().orElse(tag1.getName());
            String tag2NameV = Arrays.stream(listTestCase).filter(f -> false).map(TestCaseTagResponse::getName).findFirst().orElse(tag2.getName());

            apiVerify.verifyAddendumTagTestCase(tag1NameV, tag1)
                    .verifyAddendumTagTestCase(tag2NameV, tag2);
        });

        step("Проверяем через ui, что tag добавлен в test case ", () -> {
            testCasePages.openPageTestCase(PROJECT_ID, testCaseId);
            testCasePages.verifyAddendumTagTestCase(tag1Name)
                    .verifyAddendumTagTestCase(tag2Name);
        });
    }

    @Test
    @Epic("API")
    @Owner("толстов вадим")
    @ApiTest
    @Regress
    @WithLogin
    @DisplayName("Добавление коментарий к test case")
    void commentTestCase() {
        String comment = dataGenerator.getRandomSentence(5);

        TestCaseCommentDto requestComment = new TestCaseCommentDto();
        requestComment.setBody(comment);
        requestComment.setTestCaseId(testCaseId);

        step("Добавляем коментарий в test case в через api", () -> {
            TestCaseCommentDto responseComment = testCaseApi.responseComment(requestComment);

            assertEquals(comment, responseComment.getBody());
        });

        step("Проверяем через ui, что коментарий в test case добавлены ", () -> {
            testCasePages.openPageTestCase(PROJECT_ID, testCaseId);
            testCasePages.verifyCommentTestCase(comment);
        });
    }

    @Test
    @Epic("API")
    @Owner("толстов вадим")
    @ApiTest
    @Regress
    @WithLogin
    @DisplayName("Добавляем шагов в test case")
    void updateTestCaseStepsTest() {
        String step1 = dataGenerator.getRandomSentence(2);
        String step2 = dataGenerator.getRandomSentence(3);
        String step3 = dataGenerator.getRandomSentence(4);

        TestCaseScenarioDto scenarioDto = new TestCaseScenarioDto()
                .addStep(new TestCaseScenarioDto.Step(step1))
                .addStep(new TestCaseScenarioDto.Step(step2))
                .addStep(new TestCaseScenarioDto.Step(step3));

        step("Добавляем шаги в test case через api", () -> {
            TestCaseScenarioDto responseTestCaseScenario = testCaseApi.response(scenarioDto, testCaseId);

            apiVerify.verifyStepTagTestCase(step1, step2, step3, responseTestCaseScenario);
        });

        step("Проверяем через ui, что шаги в test case добавлены ", () -> {
            testCasePages
                    .openPageTestCase(PROJECT_ID, testCaseId)
                    .verifyStepTestCase(step1)
                    .verifyStepTestCase(step2)
                    .verifyStepTestCase(step3);
        });
    }
}
