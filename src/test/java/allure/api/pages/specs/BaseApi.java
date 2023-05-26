package allure.api.pages.specs;

import allure.api.authorization.AuthorizationApi;
import io.restassured.builder.RequestSpecBuilder;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class BaseApi {

    protected final static RequestSpecification defaultRequestSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .addHeader("Authorization", "Bearer " + AuthorizationApi.getAuthorization().getAccessToken())
            .build();
}
