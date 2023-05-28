package tests.ui;

import data.DataGenerator;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import pages.AuthorizationUi;
import pages.ProjectPagesModal;
import pages.ProjectsPagesModal;
import pages.TestCasePagesModal;
import tests.TestBase;

import static com.codeborne.selenide.Selenide.open;

public class UiTestBase extends TestBase {
    DataGenerator dataGenerator = new DataGenerator();
    AuthorizationUi userAuthorization = new AuthorizationUi();
    ProjectsPagesModal projectsPagesModal = new ProjectsPagesModal();
    ProjectPagesModal projectPages = new ProjectPagesModal();
    TestCasePagesModal testCasePages = new TestCasePagesModal();

}

