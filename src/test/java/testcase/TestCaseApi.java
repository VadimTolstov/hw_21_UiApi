package testcase;

import com.codeborne.selenide.Selenide;
import endpoint.TestCaseEndPoint;
import io.restassured.http.ContentType;
import models.CreateTestCaseBody;
import models.CreateTestCaseResponse;
import models.specs.CreateTestCaseRequestDto;
import models.specs.TestCaseDataResponseDto;
import testcase.specs.BaseApi;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestCaseApi extends BaseApi {

    public TestCaseDataResponseDto createTestCase(CreateTestCaseRequestDto testCase) {
        return with()
                .filter(withCustomTemplates())
                .given().spec(defaultRequestSpec)
                .log().body()
                .body(testCase)
                .when()
                .post(TestCaseEndPoint.CREATE)
                .then()
                .log().status()
                .log().all()
                .statusCode(200)
                .extract().as(TestCaseDataResponseDto.class);
    }

    public void deleteTestCase(Long testCaseId) {
        with().filter(withCustomTemplates())
                .spec(defaultRequestSpec)
                .pathParam("id", testCaseId)
                .when()
                .delete(TestCaseEndPoint.DELETE)
                .then()
                .statusCode(anyOf(is(200), is(202), is(204)));
    }

//    public CreateTestCaseResponse changingNameTestCase(CreateTestCaseBody body) {
//        return given().log().body()
//                .filter(withCustomTemplates())
//                .contentType(ContentType.JSON)
//                .header("Authorization", "Bearer " + accessToken)
//                .queryParam("projectId", PROJECT_ID)
//                .queryParam("leafId", testCaseId)
//                .body(body)
//                .when()
//                .post("api/rs/testcasetree/leaf/rename")
//                .then().log().body()
//                .statusCode(200)
//                .extract().as(CreateTestCaseResponse.class));
//
//        step("Api verify  changing in response", () ->
//                assertEquals(changing, createTestCaseResponse.getName()));
//
//        step("Проверяем, что changing test cases изменилось ", () -> {
//            Selenide.open("https://allure.autotests.cloud/project/" + PROJECT_ID + "/test-cases/" + testCaseId);
//            $(".TestCaseLayout__name").shouldHave(text(createTestCaseResponse.getName()));
//        });

}