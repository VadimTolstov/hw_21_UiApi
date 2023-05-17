package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import models.data.TestCaseApiDataGenerator;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    TestCaseApiDataGenerator testCaseApiDataGenerator = new TestCaseApiDataGenerator();

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://allure.autotests.cloud";
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";

        RestAssured.baseURI = "https://allure.autotests.cloud";
    }
}
