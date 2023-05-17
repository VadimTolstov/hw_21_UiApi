package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverProvider;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import models.data.TestCaseApiDataGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static config.WebDriverProvider.remoteUrl;

public class TestBase {
    TestCaseApiDataGenerator testCaseApiDataGenerator = new TestCaseApiDataGenerator();

    @BeforeAll
    static void setUp() {
//        Configuration.baseUrl = "https://allure.autotests.cloud";
//        Configuration.holdBrowserOpen = true;
//        Configuration.browserSize = "1920x1080";
        WebDriverProvider.configure();

        RestAssured.baseURI = "https://allure.autotests.cloud";

//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
//                "enableVNC", true,
//                "enableVideo", true
//        ));
//        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Selenide.closeWebDriver();
        if (remoteUrl != null) {
            Attach.addVideo();
        }
    }
}
