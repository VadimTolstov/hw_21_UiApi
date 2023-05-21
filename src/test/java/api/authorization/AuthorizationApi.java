package api.authorization;

import config.Auth;
import api.endpoint.AuthEndPoint;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class AuthorizationApi {

    private static final String ALLURE_TESTOPS_SESSION = "ALLURE_TESTOPS_SESSION";

    public static AuthorizationResponseDto getAuthorization() {
        AuthorizationResponseDto authorizationResponse = with()
                .filter(withCustomTemplates())
                .formParam("grant_type", "apitoken")
                .formParam("scope", "openid")
                .formParam("token", Auth.config.apiToken())
                .when()
                .post(AuthEndPoint.OAUTH_TOKEN)
                .then().log().all()
                .statusCode(200)
                .extract().as(AuthorizationResponseDto.class);
        return authorizationResponse;
    }

    public static String getAuthorizationCookie() {
        String xsrfToken = getAuthorization().getJti();
        return with().filter(withCustomTemplates())
                .header("X-XSRF-TOKEN", xsrfToken)
                .header("Cookie", "XSRF-TOKEN=" + xsrfToken)
                .formParam("username", Auth.config.userNameAllure())
                .formParam("password", Auth.config.passwordAllure())
                .when()
                .post(AuthEndPoint.LOGIN)
                .then()
                .statusCode(200).extract().response()
                .getCookie(ALLURE_TESTOPS_SESSION);
    }
}

