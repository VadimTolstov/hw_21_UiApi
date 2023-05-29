package helpers;

import config.ProjectConfig;
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
}