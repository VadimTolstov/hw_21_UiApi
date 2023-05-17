package testcase;

import endpoint.TestCaseEndPoint;
import models.specs.CreateTestCaseRequestDto;
import models.specs.TestCaseDataResponseDto;
import testcase.specs.BaseApi;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;


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
}


