package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriver;
import config.WebDriverProvider;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import data.TestCaseDataGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    TestCaseDataGenerator testCaseDataGenerator = new TestCaseDataGenerator();

    @BeforeAll
    static void setUp() {
        WebDriverProvider.configure();

        RestAssured.baseURI = "https://allure.autotests.cloud";
    }

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        if (WebDriver.config.getRemoteUrl() != null ) {
            Attach.addVideo();
        }
        Selenide.closeWebDriver();
    }
}
