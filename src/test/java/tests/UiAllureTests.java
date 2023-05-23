package tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Test;
import ui.pages.AuthorizationUi;
import ui.pages.ProjectsPagesModal;

public class UiAllureTests extends TestBase {
    AuthorizationUi userAuthorization = new AuthorizationUi();
    ProjectsPagesModal projectsPagesModal = new ProjectsPagesModal();

    @Test
    @Step("Открываем старницу с проектами")
    void userAuthorization() {
        userAuthorization.authorizationUi();
        projectsPagesModal.verifyProjectsPages();
    }

   // @Test
}
