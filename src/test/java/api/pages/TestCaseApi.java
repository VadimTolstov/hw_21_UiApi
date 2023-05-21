package api.pages;

import api.endpoint.TestCaseEndPoint;
import api.models.*;
import api.models.specs.Specs;
import api.pages.specs.BaseApi;
import io.qameta.allure.Step;

import static api.models.specs.Specs.request;
import static api.models.specs.Specs.response;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;


public class TestCaseApi extends BaseApi {

    @Step("Создаем test case")
    public TestCaseDataResponseDto createTestCase(CreateTestCaseRequestDto testCase) {
        return with()
                .filter(withCustomTemplates())
                .given().spec(defaultRequestSpec)
                .log().body()
                .body(testCase)
                .when()
                .post(TestCaseEndPoint.CREATE)
                .then()
                .spec(Specs.response)
                .extract().as(TestCaseDataResponseDto.class);
    }

    @Step("Удаляем test case")
    public void deleteTestCase(Long testCaseId) {
        with().filter(withCustomTemplates())
                .spec(defaultRequestSpec)
                .pathParam("id", testCaseId)
                .when()
                .delete(TestCaseEndPoint.DELETE)
                .then()
                .statusCode(anyOf(is(200), is(202), is(204)));
    }

    @Step("Редактируем имя test cases через Api")
    public CreateTestCaseResponse createTestCaseResponse(CreateTestCaseBody body, Long PROJECT_ID, Long testCaseId) {
        return given(request)
                .queryParam("projectId", PROJECT_ID)
                .queryParam("leafId", testCaseId)
                .body(body)
                .when()
                .post("testcasetree/leaf/rename")
                .then()
                .spec(response)
                .extract().as(CreateTestCaseResponse.class);
    }

    @Step("Добавляем описания в test case через Api")
    public TestCaseDataResponseDto descriptionTestCase(DescriptionTestCaseDto descriptionTestCaseDto, Long testCaseId) {
        return given(request)
                .body(descriptionTestCaseDto)
                .when()
                .patch("testcase/" + testCaseId)
                .then().spec(response)
                .extract().as(TestCaseDataResponseDto.class);
    }

    @Step("Обновляем шагов в test case")
    public TestCaseScenarioDto response(TestCaseScenarioDto scenarioDto, Long testCaseId) {
        return given(request)
                .body(scenarioDto)
                .when()
                .post("testcase/" + testCaseId + "/scenario")
                .then()
                .spec(Specs.response)
                .extract().as(TestCaseScenarioDto.class);
    }

    @Step("Добовляем comment в test case")
    public TestCaseCommentDto responseComment(TestCaseCommentDto requestComment) {
      return   given(request)
                .body(requestComment)
                .when()
                .post("comment")
                .then()
                .spec(response)
                .extract().as(TestCaseCommentDto.class);
    }
}