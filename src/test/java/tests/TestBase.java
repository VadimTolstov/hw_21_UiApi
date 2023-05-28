package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.Project;
import drivers.WebDriverProvider;
import helpers.Attachments;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.sessionId;

public class TestBase {

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
        attachEnvDependingTestArtifacts();
        Selenide.closeWebDriver();
    }

    private void attachEnvDependingTestArtifacts() {
        Attachments.pageSource();
        String sessionId = Attachments.getSessionId();
        switch (Project.config.platform()) {
            case "remote":
                Attachments.screenshotAs("Last screenshot");
                Attachments.browserConsoleLogs();
                Attachments.addVideo(sessionId);
                break;
            case "local":
                Attachments.screenshotAs("Last screenshot");
                Attachments.browserConsoleLogs();
                break;
        }
    }
}
