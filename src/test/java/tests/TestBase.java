package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.Project;
import drivers.WebDriverProvider;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import data.DataGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    DataGenerator dataGenerator = new DataGenerator();

    @BeforeAll
    static void setUp() {
        selectDriver();
    }

    private static void selectDriver() {
        switch (Project.config.platform()) {
            case "local":
            case "remote":
                WebDriverProvider.configure();
                break;
        }
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
        if (Project.config.remoteDriverUrl() != null) {
            Attach.addVideo();
        }
        Selenide.closeWebDriver();
    }

    private void attachEnvDependingTestArtifacts() {

    }
}
