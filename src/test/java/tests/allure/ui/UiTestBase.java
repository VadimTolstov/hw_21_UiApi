package tests.allure.ui;

import data.DataGenerator;
import ui.pages.AuthorizationUi;
import ui.pages.ProjectPagesModal;
import ui.pages.ProjectsPagesModal;
import ui.pages.TestCasePagesModal;
import tests.TestBase;

import static com.codeborne.selenide.Selenide.open;

public class UiTestBase extends TestBase {
    DataGenerator dataGenerator = new DataGenerator();
    AuthorizationUi userAuthorization = new AuthorizationUi();
    ProjectsPagesModal projectsPagesModal = new ProjectsPagesModal();
    ProjectPagesModal projectPages = new ProjectPagesModal();
    TestCasePagesModal testCasePages = new TestCasePagesModal();

}

