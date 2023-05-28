package allure.api.pages;

import allure.api.endpoint.TestCaseEndPoint;
import allure.api.models.*;
import allure.api.models.specs.Specs;
import allure.api.pages.specs.BaseApi;
import allure.helpers.CustomAllureListener;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;


public class TestCaseApi extends BaseApi {

    @Step("Создаем test case")
    public TestCaseDataResponseDto createTestCase(CreateTestCaseRequestDto testCase) {
        return with()
                .filter(CustomAllureListener.withCustomTemplates())
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
        with().filter(CustomAllureListener.withCustomTemplates())
                .spec(defaultRequestSpec)
                .pathParam("id", testCaseId)
                .when()
                .delete(TestCaseEndPoint.DELETE)
                .then()
                .statusCode(anyOf(is(200), is(202), is(204)));
    }

    @Step("Редактируем имя test cases через Api")
    public CreateTestCaseResponse createTestCaseResponse(CreateTestCaseBody body, Long PROJECT_ID, Long testCaseId) {
        return given(Specs.request)
                .queryParam("projectId", PROJECT_ID)
                .queryParam("leafId", testCaseId)
                .body(body)
                .when()
                .post("testcasetree/leaf/rename")
                .then()
                .spec(Specs.response)
                .extract().as(CreateTestCaseResponse.class);
    }

    @Step("Добавляем описания в test case через Api")
    public TestCaseDataResponseDto descriptionTestCase(DescriptionTestCaseDto descriptionTestCaseDto, Long testCaseId) {
        return given(Specs.request)
                .body(descriptionTestCaseDto)
                .when()
                .patch("testcase/" + testCaseId)
                .then().spec(Specs.response)
                .extract().as(TestCaseDataResponseDto.class);
    }

    @Step("Обновляем шагов в test case через Api")
    public TestCaseScenarioDto response(TestCaseScenarioDto scenarioDto, Long testCaseId) {
        return given(Specs.request)
                .body(scenarioDto)
                .when()
                .post("testcase/" + testCaseId + "/scenario")
                .then()
                .spec(Specs.response)
                .extract().as(TestCaseScenarioDto.class);
    }

    @Step("Добовляем comment в test case через Api")
    public TestCaseCommentDto responseComment(TestCaseCommentDto requestComment) {
        return given(Specs.request)
                .body(requestComment)
                .when()
                .post("comment")
                .then()
                .spec(Specs.response)
                .extract().as(TestCaseCommentDto.class);
    }

    @Step("Добавляем tag к test case через Api")
    public ValidatableResponse addendumResponse(List list, Long testCaseId) {
        return given(Specs.request)
                .body(list)
                .when()
                .post("testcase/" + testCaseId + "/tag")
                .then().log().body();
    }
}