package helpers;

import config.ProjectConfig;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;

public class Browserstack {

  static final ProjectConfig CFG = ConfigFactory.create(ProjectConfig.class);
    public static String videoUrl(String sessionId) {
        String url = format(CFG.getSessionsUrl(), sessionId);

        return given()
                .log().all()
                .auth().basic(CFG.getBrowserstackUser(), CFG.getBrowserstackKey())
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .extract().path("automation_session.video_url");
    }
//public static String videoUrl(String sessionId) {
//    return getSessionInfo(sessionId)
//            .path("automation_session.video_url");
//}
//
//    public static String fullInfoPublicUrl(String sessionId) {
//        return getSessionInfo(sessionId)
//                .path("automation_session.public_url");
//    }
//
//    private static ExtractableResponse<Response> getSessionInfo(String sessionId) {
//        return given()
//                .auth().basic(CFG.getBrowserstackUser(), CFG.getBrowserstackKey())
//                .when()
//                .get("https://api.browserstack.com/app-automate/sessions/" + sessionId +".json")
//                .then()
//                .statusCode(200)
//                .extract();
//    }
}