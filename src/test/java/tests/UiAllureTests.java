package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pages.TestCasePagesModal;
import ui.pages.AuthorizationUi;
import ui.pages.ProjectPagesModal;
import ui.pages.ProjectsPagesModal;

@DisplayName("Ui tests Aller")
public class UiAllureTests extends TestBase {
    AuthorizationUi userAuthorization = new AuthorizationUi();
    ProjectsPagesModal projectsPagesModal = new ProjectsPagesModal();
    ProjectPagesModal projectPages = new ProjectPagesModal();
    TestCasePagesModal testCasePages = new TestCasePagesModal();

    @BeforeEach
    @DisplayName("Создаем проект")
    public void createProject() {
        String newProject = dataGenerator.getNewProject();

        userAuthorization.authorizationUi();

        projectsPagesModal
                .verifyProjectsPages()
                .openModalCreateNewProject()
                .createProject(newProject);

        projectPages.verifyNewProject(newProject);
    }

    @AfterEach
    @DisplayName("Удаляем проект")
    public void deleteProject() {
        projectPages.deleteProject();
    }

    @Test
    @DisplayName("Создаем test case в проекте")
    public void createTestCase() {
        String nameTestCase = dataGenerator.getTestCaseName();

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .verifyTestCaseName(nameTestCase);
    }

    @Test
    @DisplayName("Добовляем шаги в test case")
    public void createStepTestCase() {
        String nameTestCase = dataGenerator.getTestCaseName();
        String step1 =dataGenerator.getStepTestCaseOne();
        String step2 = dataGenerator.getStepTestCaseTwo();
        String step3 = dataGenerator.getStepTestCaseThree();

        testCasePages
                .openTestCasesPages()
                .createTestCase(nameTestCase)
                .openTestCase(nameTestCase)


    }
//    @Test
//    @DisplayName("")
//    public void createNewProject() {
//        userAuthorization.authorizationUi();
//
//        projectsPagesModal
//                .openModalCreateNewProject()
//                .createProject(newProject);
//
//        projectPages
//                .verifyNewProject(newProject)
//                .deleteProject();
//    }
}
