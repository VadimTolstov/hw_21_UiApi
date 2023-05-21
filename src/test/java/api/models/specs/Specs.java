package api.models.specs;

import api.authorization.AuthorizationApi;
import helpers.CustomAllureListener;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class Specs {

   public static RequestSpecification request = with()
           .filter(CustomAllureListener.withCustomTemplates())
           .log().uri()
           .log().body()
           //.baseUri() todo .baseUri(Project.config.apiBaseUrl())
           .contentType(ContentType.JSON)
           .header("Authorization", "Bearer " + AuthorizationApi.getAuthorization().getAccessToken())
           .basePath("api/rs/");

   public static ResponseSpecification response = new ResponseSpecBuilder()
           .log(LogDetail.ALL)
           .expectStatusCode(200)
           .build();
}
