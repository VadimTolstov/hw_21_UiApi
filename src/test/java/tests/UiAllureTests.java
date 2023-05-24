package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.AuthorizationUi;
import ui.pages.ProjectPagesModal;
import ui.pages.ProjectsPagesModal;

@DisplayName("Ui tests Aller")
public class UiAllureTests extends TestBase {
    AuthorizationUi userAuthorization = new AuthorizationUi();
    ProjectsPagesModal projectsPagesModal = new ProjectsPagesModal();
    ProjectPagesModal projectPages = new ProjectPagesModal();

    String newProject = dataGenerator.getNewProject();

    // @Feature("Что то за тэг") todo
    @Test
    @DisplayName("Открываем старницу с проектами")
    void userAuthorization() {
        userAuthorization.authorizationUi();
        projectsPagesModal.verifyProjectsPages();
    }

    @Test
    @DisplayName("Создаем новый проект")
    void createNewProject() {
        userAuthorization.authorizationUi();

        projectsPagesModal
                .openModalCreateNewProject()
                .createProject(newProject);

        projectPages
                .verifyNewProject(newProject)
                .deleteProject();

    }
}
